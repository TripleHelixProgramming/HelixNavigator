package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.waypoint.HWaypoint.WaypointType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);
    private final DoubleProperty zoomTranslateX = new SimpleDoubleProperty(this, "zoomTranslateX", 0.0);
    private final DoubleProperty zoomTranslateY = new SimpleDoubleProperty(this, "zoomTranslateY", 0.0);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
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
        translateXProperty().bind(zoomTranslateX.add(x.multiply(zoomScale)).subtract(11.0)); // tx = ztx + zs*x
        // For y you must subtract to flip the coordinates, in graphics +y is down but in robotics +y is up:
        translateYProperty().bind(zoomTranslateY.subtract(y.multiply(zoomScale)).subtract(11.0)); // ty = zty + zs*y
        selected.addListener((currentValue, oldValue, newValue) -> {
            selectionCircle.setOpacity(newValue ? 1.0 : 0.0);
        });
    }

    private void disableCross() {
        cross1.setOpacity(0.0);
        cross2.setOpacity(0.0);
    }
    private void enableCross() {
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
        return x;
    }

    public final void setX(double value) {
        x.set(value);
    }

    public final double getX() {
        return x.get();
    }

    public final DoubleProperty yProperty() {
        return y;
    }

    public final void setY(double value) {
        y.set(value);
    }

    public final double getY() {
        return y.get();
    }

    public final DoubleProperty zoomTranslateXProperty() {
        return zoomTranslateX;
    }

    public final void setZoomTranslateX(double value) {
        zoomTranslateX.set(value);
    }

    public final double getZoomTranslateX() {
        return zoomTranslateX.get();
    }

    public final DoubleProperty zoomTranslateYProperty() {
        return zoomTranslateY;
    }

    public final void setZoomTranslateY(double value) {
        zoomTranslateY.set(value);
    }

    public final double getZoomTranslateY() {
        return zoomTranslateY.get();
    }

    public final DoubleProperty zoomScaleProperty() {
        return zoomScale;
    }

    public final void setZoomScale(double value) {
        zoomScale.set(value);
    }

    public final double getZoomScale() {
        return zoomScale.get();
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
