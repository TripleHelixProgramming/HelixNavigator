package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.waypoint.HWaypoint.WaypointType;
import org.team2363.helixnavigator.ui.editor.PathElementView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

public class WaypointView implements PathElementView {

    private final Circle selectionCircle = new Circle(12.0);
    private final Circle waypointCircle = new Circle(10.0);
    private final Line cross1 = new Line(-4, 4, 4, -4);
    private final Line cross2 = new Line(-4, -4, 4, 4);
    private final Circle clip = new Circle(12.0);
    private final Pane pane = new Pane();

    private final ObjectProperty<WaypointType> waypointType = new SimpleObjectProperty<>(this, "waypointType", WaypointType.HARD);
    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    private final Translate centerTranslate = new Translate();

    public WaypointView() {
        selectionCircle.setFill(Color.ORANGE);
        waypointCircle.setFill(Color.BLUE);
        
        waypointCircle.setStroke(Color.BLACK);
        waypointCircle.setStrokeType(StrokeType.INSIDE);
        waypointCircle.setStrokeWidth(3.0);
        selectionCircle.setOpacity(0.0);
        cross1.setStrokeWidth(3);
        cross2.setStrokeWidth(3);
        cross1.setStroke(Color.RED);
        cross2.setStroke(Color.RED);
        pane.getChildren().addAll(selectionCircle, waypointCircle, cross1, cross2);
        pane.setClip(clip);

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
        // For y you must subtract to flip the coordinates, in graphics +y is down but in math/engineering/robotics +y is up (i think):
        centerTranslate.xProperty().bind(x.multiply(zoomScale));
        centerTranslate.yProperty().bind(y.multiply(zoomScale).negate());
        pane.getTransforms().add(centerTranslate);
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

    public final DoubleProperty zoomScaleProperty() {
        return zoomScale;
    }

    public final void setZoomScale(double value) {
        zoomScale.set(value);
    }

    public final double getZoomScale() {
        return zoomScale.get();
    }

    @Override
    public final BooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public final void setSelected(boolean value) {
        selected.set(value);
    }

    @Override
    public final boolean getSelected() {
        return selected.get();
    }

    @Override
    public Pane getView() {
        return pane;
    }
}
