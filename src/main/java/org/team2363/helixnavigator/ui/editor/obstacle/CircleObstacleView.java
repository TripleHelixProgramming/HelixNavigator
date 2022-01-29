package org.team2363.helixnavigator.ui.editor.obstacle;

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

public class CircleObstacleView implements PathElementView {

    private final Circle circle = new Circle();
    private final Pane pane = new Pane();

    private final DoubleProperty centerX = new SimpleDoubleProperty(this, "centerX", 0.0);
    private final DoubleProperty centerY = new SimpleDoubleProperty(this, "centerY", 0.0);
    private final DoubleProperty radius = new SimpleDoubleProperty(this, "radius", 0.0);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    private final Translate centerTranslate = new Translate();

    public CircleObstacleView() {

        circle.setStroke(Color.ORANGE);
        circle.setStrokeWidth(0.0);
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setFill(Color.CYAN);

        pane.getChildren().addAll(circle);
        pane.setPickOnBounds(false);

        centerTranslate.xProperty().bind(centerX.multiply(zoomScale));
        centerTranslate.yProperty().bind(centerY.multiply(zoomScale).negate());
        pane.getTransforms().addAll(centerTranslate);

        circle.radiusProperty().bind(radius.multiply(zoomScale));
        
        selected.addListener((currentValue, oldValue, newValue) -> {
            circle.setStrokeWidth(newValue ? 2.0 : 0.0);
        });
    }

    public final DoubleProperty centerXProperty() {
        return centerX;
    }

    public final void setCenterX(double value) {
        centerX.set(value);
    }

    public final double getCenterX() {
        return centerX.get();
    }

    public final DoubleProperty centerYProperty() {
        return centerY;
    }

    public final void setCenterY(double value) {
        centerY.set(value);
    }

    public final double getCenterY() {
        return centerY.get();
    }

    public final DoubleProperty radiusProperty() {
        return radius;
    }

    public final void setRadius(double value) {
        radius.set(value);
    }

    public final double getRadius() {
        return radius.get();
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
