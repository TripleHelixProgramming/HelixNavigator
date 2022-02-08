package org.team2363.helixnavigator.ui.editor.waypoint;

import java.util.List;

import org.team2363.helixnavigator.document.DocumentManager;
import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.lib.ui.MouseEventWrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Some thoughts I've had about dragging events:
 * 1. Each drag event should be triggered by only one {@code MouseEvent}
 * 2. The origin of the coordinate system of the event (event.getX() or event.getY()) should correspond to the origin of the field
 * What this means is that the {@code Node} that triggers the event must be a child of WaypointPane, ObstaclePane, etc.
 * Events also shouldn't stack. For example, let's say that circle obstacles have a ring at the edge that you can drag to increase the
 * radius of the obstacle. When that is dragged, it can't also trigger the translational drag event.
 */
public class WaypointsPane extends Pane {

    private final DocumentManager documentManager;

    private final Pane waypointsPane = new Pane();
    private final Pane robotsPane = new Pane();

    private final ChangeListener<? super HPath> onSelectedPathChanged = this::selectedPathChanged;
    private final ListChangeListener<? super HWaypoint> onWaypointsChanged = this::waypointsChanged;
    
    public WaypointsPane(DocumentManager documentManager) {
        this.documentManager = documentManager;

        waypointsPane.setPickOnBounds(false);
        robotsPane.setPickOnBounds(false);
        getChildren().addAll(waypointsPane, robotsPane);
        setPickOnBounds(false);

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
            getChildren().clear();
            oldPath.getWaypoints().removeListener(onWaypointsChanged);
        }
    }

    private void loadSelectedPath(HPath newPath) {
        if (newPath != null) {
            updateWaypoints(newPath.getWaypoints());
            newPath.getWaypoints().addListener(onWaypointsChanged);
        }
    }

    private void waypointsChanged(ListChangeListener.Change<? extends HWaypoint> change) {
        updateWaypoints(change.getList());
    }

    private void updateWaypoints(List<? extends HWaypoint> list) {
        waypointsPane.getChildren().clear();
        robotsPane.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            HWaypoint waypoint = list.get(i);
            WaypointView waypointView;
            switch (waypoint.getWaypointType()) {
                case SOFT:
                    HSoftWaypoint softWaypoint = (HSoftWaypoint) waypoint;
                    SoftWaypointView softWaypointView = new SoftWaypointView(softWaypoint);
                    waypointView = softWaypointView;
                    break;
                case HARD:
                    HHardWaypoint hardWaypoint = (HHardWaypoint) waypoint;
                    HardWaypointView hardWaypointView = new HardWaypointView(hardWaypoint);
                    hardWaypointView.bumperLengthProperty().bind(this.documentManager.getDocument().getRobotConfiguration().bumperLengthProperty());
                    hardWaypointView.bumperWidthProperty().bind(this.documentManager.getDocument().getRobotConfiguration().bumperWidthProperty());
                    robotsPane.getChildren().add(hardWaypointView.getRobotView().getView());
                    waypointView = hardWaypointView;
                    break;
                default:
                    waypointView = null;
                    break;
            }
            linkWaypointView(i, waypointView, waypoint);
            waypointsPane.getChildren().add(i, waypointView.getView());
        }
    }

    private void linkWaypointView(final int index, final WaypointView waypointView, final HWaypoint waypoint) {
        waypointView.zoomScaleProperty().bind(documentManager.getDocument().zoomScaleProperty());

        EventHandler<MouseEvent> onMousePressed = event -> {
        };
        EventHandler<MouseEvent> onMouseDragBegin = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!event.isShortcutDown() && !documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().isSelected(index)) {
                    documentManager.getDocument().getSelectedPath().clearSelection();
                }
                documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().select(index);
                // documentManager.actions().handleMouseDragBeginAsElementsDragBegin(event);
            }
        };
        EventHandler<MouseEvent> onMouseDragged = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                documentManager.actions().handleMouseDraggedAsWaypointDragged(event, waypoint);
            }
        };
        EventHandler<MouseEvent> onMouseDragEnd = event -> {
        };
        EventHandler<MouseEvent> onMouseReleased = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!event.isShortcutDown()) {
                    boolean selected = documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().isSelected(index);
                    documentManager.getDocument().getSelectedPath().clearSelection();
                    documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().setSelected(index, selected);
                }
                documentManager.getDocument().getSelectedPath().getWaypointsSelectionModel().toggle(index);
            }
        };

        MouseEventWrapper eventWrapper = new MouseEventWrapper(onMousePressed, onMouseDragBegin, onMouseDragged, onMouseDragEnd, onMouseReleased);
        waypointView.getView().setOnMousePressed(eventWrapper.getOnMousePressed());
        waypointView.getView().setOnMouseDragged(eventWrapper.getOnMouseDragged());
        waypointView.getView().setOnMouseReleased(eventWrapper.getOnMouseReleased());
    }
}