package com.team2363.helixnavigator.ui.editor;

import com.team2363.helixnavigator.document.waypoint.HAbstractWaypoint;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class WaypointView extends StackPane {
    private static final double selectionRadius = 8.0;
    private static final double waypointRadius = 5.0;
    private final Circle waypointCircle = new Circle();
    private final Circle selectionCircle = new Circle();
    private final HAbstractWaypoint waypoint;
    public WaypointView(HAbstractWaypoint waypoint) {
        this.waypoint = waypoint;
        waypointCircle.centerXProperty().bind(this.waypoint.xProperty());
        waypointCircle.centerYProperty().bind(this.waypoint.yProperty());
        selectionCircle.centerXProperty().bind(this.waypoint.xProperty());
        selectionCircle.centerYProperty().bind(this.waypoint.yProperty());
        this.waypoint.selectedProperty().addListener((currentVal, oldVal, newVal) -> {
            selectionCircle.setDisable(!newVal);
        });
        waypointCircle.setOnMouseDragged(event -> {
            waypoint.setX(event.getSceneX());
            waypoint.setY(event.getSceneY());
        });

        waypointCircle.setRadius(waypointRadius);
        waypointCircle.setFill(Color.PURPLE);
        selectionCircle.setRadius(selectionRadius);
        selectionCircle.setFill(Color.ORANGE);
    }
}
