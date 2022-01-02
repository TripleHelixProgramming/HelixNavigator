package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.waypoint.HWaypoint.WaypointType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class WaypointView extends StackPane {

    private final Circle waypointCircle = new Circle(8.0);
    private final Circle selectionCircle = new Circle(11.0);
    private final Rectangle cross1 = new Rectangle(11, 3);
    private final Rectangle cross2 = new Rectangle(11, 3);

    private final ObjectProperty<WaypointType> waypointType = new SimpleObjectProperty<>(this, "waypointType", WaypointType.SOFT);
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    public WaypointView() {
        waypointCircle.setFill(Color.BLUE);
        selectionCircle.setFill(Color.ORANGE);
        selectionCircle.setOpacity(0.0);
        cross1.setRotate(45);
        cross2.setRotate(-45);
        cross1.setFill(Color.RED);
        cross2.setFill(Color.RED);
        disableCross();
        getChildren().addAll(selectionCircle, waypointCircle, cross1, cross2);

        waypointType.addListener((currentValue, oldValue, newValue) -> {
            switch (newValue) {
                case SOFT:
                    disableCross();
                    break;
                case HARD:
                    enableCross();
                    break;
            }
        });
        selected.addListener((currentValue, oldValue, newValue) -> {
            System.out.println("Selected: " + newValue);
            selectionCircle.setOpacity(newValue ? 1.0 : 0.0);
        });
    }

    private void disableCross() {
        System.out.println("Disabling cross");
        cross1.setOpacity(0.0);
        cross2.setOpacity(0.0);
    }
    private void enableCross() {
        System.out.println("Enabling cross");
        cross1.setOpacity(1.0);
        cross2.setOpacity(1.0);
    }

    public final ObjectProperty<WaypointType> waypointTypeProperty() {
        return waypointType;
    }

    public final void setWaypointType(WaypointType value) {
        waypointType.set(value);
    }

    public final WaypointType getWaypointType() {
        return waypointType.get();
    }

    public final DoubleProperty xProperty() {
        return translateXProperty();
    }

    public final void setX(double value) {
        setTranslateX(value);
    }

    public final double getX() {
        return getTranslateX();
    }

    public final DoubleProperty yProperty() {
        return translateYProperty();
    }

    public final void setY(double value) {
        setTranslateY(value);
    }

    public final double getY() {
        return getTranslateY();
    }

    public final BooleanProperty selectedProperty() {
        return selected;
    }

    public final void setSelected(boolean value) {
        selected.set(value);
    }

    public final boolean getSelected() {
        return selected.get();
    }
}
