package org.team2363.helixnavigator.ui.editor.waypoint;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.lib.ui.MouseEventWrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
            unloadSelectedPath(oldDocument.getSelectedPath());
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
        }
    }

    private void waypointsChanged(ListChangeListener.Change<? extends HWaypoint> change) {
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
        waypointView.zoomTranslateXProperty().bind(this.documentManager.getDocument().zoomTranslateXProperty());
        waypointView.zoomTranslateYProperty().bind(this.documentManager.getDocument().zoomTranslateYProperty());
        waypointView.zoomScaleProperty().bind(this.documentManager.getDocument().zoomScaleProperty());

        EventHandler<MouseEvent> onMousePressed = event -> {

        };
        EventHandler<MouseEvent> onMouseDragBegin = event -> {
            documentManager.getDocument().handleElementsDragBegin(event);
        };
        EventHandler<MouseEvent> onMouseDragged = event -> {
            documentManager.getDocument().handleElementsDragged(event);
        };
        EventHandler<MouseEvent> onMouseDragEnd = event -> {

        };
        EventHandler<MouseEvent> onMouseReleased = event -> {
            if (!event.isShortcutDown()) {
                documentManager.getDocument().getSelectedPath().clearSelection();
            }
            documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().toggle(index);
        };

        MouseEventWrapper eventWrapper = new MouseEventWrapper(onMousePressed, onMouseDragBegin, onMouseDragged, onMouseDragEnd, onMouseReleased);
        waypointView.setOnMousePressed(eventWrapper.getOnMousePressed());
        waypointView.setOnMouseDragged(eventWrapper.getOnMouseDragged());
        waypointView.setOnMouseReleased(eventWrapper.getOnMouseReleased());
    }
}