package org.team2363.helixnavigator.ui.editor.waypoint;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class WaypointView extends StackPane {

    private final Circle waypointCircle = new Circle(8.0);
    private final Circle selectionCircle = new Circle(11.0);

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    public WaypointView() {
        waypointCircle.setFill(Color.BLUE);
        selectionCircle.setFill(Color.ORANGE);
        selectionCircle.setOpacity(0.0);

        getChildren().addAll(selectionCircle, waypointCircle);

        selected.addListener((currentValue, oldValue, newValue) -> {
            selectionCircle.setOpacity(newValue ? 1.0 : 0.0);
        });
    }

    public final void setOnWaypointDragged(EventHandler<MouseEvent> handler) {
        setOnMouseDragged(handler);
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
