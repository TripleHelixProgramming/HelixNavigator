package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

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
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            for (HWaypoint waypoint : newPath.getWaypoints()) {
                WaypointView waypointView = new WaypointView();
                linkWaypointView(waypointView, waypoint);
                waypointViews.add(waypointView);
                getChildren().add(waypointView);
            }
            updateSelectedWaypoints();
            newPath.getWaypoints().addListener(onWaypointsChanged);
            newPath.getWaypointsSelectionModel().getSelectedIndices().addListener(onWaypointsSelectedIndicesChanged);
        }
    }

    private void waypointsChanged(ListChangeListener.Change<? extends HWaypoint> change) {
        while (change.next()) {
            if (change.wasRemoved()) {
                waypointViews.remove(change.getFrom(), change.getFrom() + change.getRemovedSize());
                getChildren().remove(change.getFrom(), change.getFrom() + change.getRemovedSize());
            }
            if (change.wasAdded()) {
                for (int i = change.getFrom(); i < change.getTo(); i++) {
                    WaypointView waypointView = new WaypointView();
                    linkWaypointView(waypointView, change.getList().get(i));
                    waypointViews.add(i, waypointView);
                    getChildren().add(i, waypointView);
                }
            }
        }
        updateSelectedWaypoints();
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

    private void linkWaypointView(WaypointView waypointView, HWaypoint waypoint) {
        waypointView.waypointTypeProperty().bind(waypoint.waypointTypeProperty());
        waypointView.xProperty().bind(waypoint.xProperty());
        waypointView.yProperty().bind(waypoint.yProperty());
        waypointView.zoomTranslateXProperty().bind(this.documentManager.getDocument().getSelectedPath().zoomXOffsetProperty());
        waypointView.zoomTranslateYProperty().bind(this.documentManager.getDocument().getSelectedPath().zoomYOffsetProperty());
        waypointView.zoomScaleProperty().bind(this.documentManager.getDocument().getSelectedPath().zoomScaleProperty());
        // waypointView.setOnMousePressed(event -> {
        // });
        // waypointView.setOnMouseReleased(event -> {
        //     if (event.getButton() == MouseButton.PRIMARY) {
        //         documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().toggle(waypoint);
        //     }
        // });
        // waypointView.setOnMouseClicked(event -> {
        //     if (event.getButton() == MouseButton.PRIMARY) {
        //         documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().toggle(waypoint);
        //     }
        // });
        // waypointView.setOnMouseDragged(event -> {
        //     waypoint.handleMouseDragged(event);
        // });
    }
}