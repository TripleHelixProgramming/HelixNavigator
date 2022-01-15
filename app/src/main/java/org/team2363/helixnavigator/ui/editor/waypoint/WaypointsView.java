package org.team2363.helixnavigator.ui.editor.waypoint;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class WaypointsView extends Pane {

    private final DocumentManager documentManager;

    private final ObservableList<WaypointView> waypointViews = FXCollections.<WaypointView>observableArrayList();

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ListChangeListener<? super HWaypoint> onWaypointsChanged = this::waypointsChanged;
    private final ListChangeListener<? super Integer> onWaypointsSelectedIndicesChanged = this::waypointsSelectedIndicesChanged;
    
    public WaypointsView(DocumentManager documentManager) {
        this.documentManager = documentManager;

        loadDocument(this.documentManager.getDocument());
        this.documentManager.documentProperty().addListener(this::documentChanged);
    }

    private void documentChanged(ObservableValue<? extends HDocument> currentDocument, HDocument oldDocument, HDocument newDocument) {
        unloadDocument(oldDocument);
        loadDocument(newDocument);
    }

    private void unloadDocument(HDocument oldDocument) {
        if (oldDocument != null) {
            waypointViews.clear();
            getChildren().clear();
            oldDocument.selectedPathProperty().removeListener(onSelectedPathChanged);
        }
    }

    private void loadDocument(HDocument newDocument) {
        if (newDocument != null) {
            loadSelectedPath(newDocument.getSelectedPath());
            newDocument.selectedPathProperty().addListener(onSelectedPathChanged);
        }
    }

    private void selectedPathChanged(ObservableValue<? extends HPath> currentPath, HPath oldPath, HPath newPath) {
        unloadSelectedPath(oldPath);
        loadSelectedPath(newPath);
    }

    private void unloadSelectedPath(HPath oldPath) {
        if (oldPath != null) {
            waypointViews.clear();
            getChildren().clear();
            oldPath.getWaypoints().removeListener(onWaypointsChanged);
            oldPath.getWaypointsSelectionModel().getSelectedIndices().removeListener(onWaypointsSelectedIndicesChanged);
            setOnMousePressed(null);
            setOnMouseDragged(null);
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            for (int i = 0; i < newPath.getWaypoints().size(); i++) {
                WaypointView waypointView = new WaypointView();
                linkWaypointView(i, waypointView, newPath.getWaypoints().get(i));
                waypointViews.add(waypointView);
                getChildren().add(waypointView);
            }
            updateSelectedWaypoints();
            newPath.getWaypoints().addListener(onWaypointsChanged);
            newPath.getWaypointsSelectionModel().getSelectedIndices().addListener(onWaypointsSelectedIndicesChanged);
            setOnMousePressed(event -> {
                documentManager.getDocument().getSelectedPath().handleElementsPressed(event);
            });
            setOnMouseDragged(event -> {
                documentManager.getDocument().getSelectedPath().handleElementsDragged(event);
            });
        }
    }

    private void waypointsChanged(ListChangeListener.Change<? extends HWaypoint> change) {
        // while (change.next()) {
        //     if (change.wasRemoved()) {
        //         waypointViews.remove(change.getFrom(), change.getFrom() + change.getRemovedSize());
        //         getChildren().remove(change.getFrom(), change.getFrom() + change.getRemovedSize());
        //     }
        //     if (change.wasAdded()) {
        //         for (int i = change.getFrom(); i < change.getTo(); i++) {
        //             WaypointView waypointView = new WaypointView();
        //             linkWaypointView(i, waypointView, change.getList().get(i));
        //             waypointViews.add(i, waypointView);
        //             getChildren().add(i, waypointView);
        //         }
        //     }
        // }
        updateWaypoints(change.getList());
        updateSelectedWaypoints();
    }

    private void updateWaypoints(List<? extends HWaypoint> list) {
        waypointViews.clear();
        getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            WaypointView waypointView = new WaypointView();
            linkWaypointView(i, waypointView, list.get(i));
            waypointViews.add(i, waypointView);
            getChildren().add(i, waypointView);
        }
    }

    private void waypointsSelectedIndicesChanged(ListChangeListener.Change<? extends Integer> change) {
        updateSelectedWaypoints();
    }

    private void updateSelectedWaypoints() {
        for (WaypointView waypointView : waypointViews) {
            waypointView.setSelected(false);
        }
        for (int i : documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().getSelectedIndices()) {
            WaypointView wv = (WaypointView) getChildren().get(i);
            wv.setSelected(true);
        }
    }

    // Index needs to be dynamically updated
    private void linkWaypointView(int index, WaypointView waypointView, HWaypoint waypoint) {
        waypointView.waypointTypeProperty().bind(waypoint.waypointTypeProperty());
        waypointView.xProperty().bind(waypoint.xProperty());
        waypointView.yProperty().bind(waypoint.yProperty());
        waypointView.zoomTranslateXProperty().bind(this.documentManager.getDocument().getSelectedPath().zoomOffsetXProperty());
        waypointView.zoomTranslateYProperty().bind(this.documentManager.getDocument().getSelectedPath().zoomOffsetYProperty());
        waypointView.zoomScaleProperty().bind(this.documentManager.getDocument().getSelectedPath().zoomScaleProperty());

        // TWO STATES:
        // Deselected:
        //     setOnMousePressed(onPressed);
        //     setOnMouseClicked(onReleased);
        // Selected:
        //     setOnMousePressed(onPressed);
        //     setOnMouseClicked(null);
        EventHandler<MouseEvent> onReleased = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("Released");
                if (documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().isSelected(index)) {
                    documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().deselect(index);
                }
            }
        };
        EventHandler<MouseEvent> onPressed = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("Pressed");
                if (!documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().isSelected(index)) {
                    documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().select(index);
                    setOnMouseReleased(null);
                    System.out.println("Event released = " + getOnMouseReleased());
                } else {
                    setOnMouseReleased(onReleased);
                    System.out.println("Event released = " + getOnMouseReleased());
                }
            }
        };
        waypointView.setOnMousePressed(onPressed);
    }
}