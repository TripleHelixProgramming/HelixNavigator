package org.team2363.helixnavigator.ui.editor.obstacle;

import org.team2363.helixnavigator.ui.editor.PathElementView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CircleObstacleView implements PathElementView {

    private final Circle circle = new Circle(12.0);

    private final DoubleProperty centerX = new SimpleDoubleProperty(this, "centerX", 0.0);
    private final DoubleProperty centerY = new SimpleDoubleProperty(this, "centerY", 0.0);
    private final DoubleProperty radius = new SimpleDoubleProperty(this, "radius", 0.0);
    private final DoubleProperty pathAreaWidth = new SimpleDoubleProperty(this, "pathAreaWidth", 0.0);
    private final DoubleProperty pathAreaHeight = new SimpleDoubleProperty(this, "pathAreaHeight", 0.0);
    private final DoubleProperty zoomTranslateX = new SimpleDoubleProperty(this, "zoomTranslateX", 0.0);
    private final DoubleProperty zoomTranslateY = new SimpleDoubleProperty(this, "zoomTranslateY", 0.0);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    public CircleObstacleView() {

        circle.setStroke(Color.ORANGE);
        circle.setStrokeWidth(0.0);
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setFill(Color.CYAN);

        circle.centerXProperty().bind(pathAreaWidth.multiply(0.5).add(zoomTranslateX).add(centerX.multiply(zoomScale)));       // ccx = paw*0.5 + ztx + cx*zs
        circle.centerYProperty().bind(pathAreaHeight.multiply(0.5).add(zoomTranslateY).subtract(centerY.multiply(zoomScale))); // ccy = pah*0.5 + zty - cy*zs
        circle.radiusProperty().bind(radius.multiply(zoomScale));
        selected.addListener((currentValue, oldValue, newValue) -> {
            circle.setStrokeWidth(newValue ? 2.0 : 0.0);
        });
    }

    public Circle getView() {
        return circle;
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

    public final DoubleProperty pathAreaWidthProperty() {
        return pathAreaWidth;
    }

    public final void setPathAreaWidth(double value) {
        pathAreaWidth.set(value);
    }

    public final double getPathAreaWidth() {
        return pathAreaWidth.get();
    }

    public final DoubleProperty pathAreaHeightProperty() {
        return pathAreaHeight;
    }

    public final void setPathAreaHeight(double value) {
        pathAreaHeight.set(value);
    }

    public final double getPathAreaHeight() {
        return pathAreaHeight.get();
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
}
