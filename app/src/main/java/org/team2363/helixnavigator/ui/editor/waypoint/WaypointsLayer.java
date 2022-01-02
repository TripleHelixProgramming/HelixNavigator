package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.HPath;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.collections.ListChangeListener;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

public class WaypointsLayer extends Pane {

    private final HPath path;
    
    public WaypointsLayer(HPath path) {
        this.path = path;
        if (this.path != null) {
            for (HWaypoint waypoint : this.path.getWaypoints()) {
                WaypointView waypointView = new WaypointView();
                linkWaypointView(waypointView, waypoint);
                getChildren().add(waypointView);
            }
            this.path.getWaypoints().addListener(this::waypointsChanged);
            for (int i : this.path.getWaypointsSelectionModel().getSelectedIndices()) {
                WaypointView wv = (WaypointView) getChildren().get(i);
                wv.setSelected(true);
            }
            this.path.getWaypointsSelectionModel().getSelectedIndices().addListener(this::selectedIndiciesChanged); // TODO: make sure this gets removed.
        }
    }

    private void waypointsChanged(ListChangeListener.Change<? extends HWaypoint> change) {
        while (change.next()) {
            if (change.wasRemoved()) {
                getChildren().remove(change.getFrom(), change.getFrom() + change.getRemovedSize());
            }
            if (change.wasAdded()) {
                for (int i = change.getFrom(); i < change.getTo(); i++) {
                    WaypointView waypointView = new WaypointView();
                    linkWaypointView(waypointView, change.getList().get(i));
                    getChildren().add(i, waypointView);
                }
            }
        }
        updateSelectedWaypoints();
    }

    private void selectedIndiciesChanged(ListChangeListener.Change<? extends Integer> change) {
        updateSelectedWaypoints();
    }

    private void updateSelectedWaypoints() {
        for (int i = 0; i < path.getWaypoints().size(); i++) {
            WaypointView wv = (WaypointView) getChildren().get(i);
            wv.setSelected(false);
        }
        for (int i : path.getWaypointsSelectionModel().getSelectedIndices()) {
            WaypointView wv = (WaypointView) getChildren().get(i);
            wv.setSelected(true);
        }
    }

    private void linkWaypointView(WaypointView waypointView, HWaypoint waypoint) {
        waypointView.waypointTypeProperty().bind(waypoint.waypointTypeProperty());
        waypointView.xProperty().bind(waypoint.xProperty());
        waypointView.yProperty().bind(waypoint.yProperty());
        waypointView.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!path.getWaypointsSelectionModel().getSelectedItems().contains(waypoint)) {
                    path.getWaypointsSelectionModel().clear();
                    path.getWaypointsSelectionModel().toggle(waypoint);
                }
            }
        });
        waypointView.setOnMouseDragged(event -> {
            waypoint.handleMouseDragged(event);
        });
    }

    public void removeListeners() {
        if (path != null) {
            path.getWaypoints().removeListener(this::waypointsChanged);
            path.getWaypointsSelectionModel().getSelectedIndices().removeListener(this::selectedIndiciesChanged);
        }
    }
}