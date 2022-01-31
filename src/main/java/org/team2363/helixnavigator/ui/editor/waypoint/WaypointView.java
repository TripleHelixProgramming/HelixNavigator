package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.ui.editor.PathElementView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

public abstract class WaypointView implements PathElementView {

    private final Circle selectionCircle = new Circle(12.0);
    private final Circle waypointCircle = new Circle(10.0);
    private final Pane pane = new Pane();

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
        
        pane.getChildren().addAll(selectionCircle, waypointCircle);
        pane.setPickOnBounds(false);

        // For y you must subtract to flip the coordinates, in graphics +y is down but in math/engineering/robotics +y is up (i think):
        centerTranslate.xProperty().bind(x.multiply(zoomScale));
        centerTranslate.yProperty().bind(y.multiply(zoomScale).negate());
        pane.getTransforms().add(centerTranslate);
        selected.addListener((currentValue, oldValue, newValue) -> {
            selectionCircle.setOpacity(newValue ? 1.0 : 0.0);
        });
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
