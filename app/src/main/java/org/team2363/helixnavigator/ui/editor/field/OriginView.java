package org.team2363.helixnavigator.ui.editor.field;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OriginView extends Pane {
    
    private final Circle outerCircle = new Circle(7);
    private final Circle middleCircle = new Circle(5);
    private final Circle innerCircle = new Circle(3);

    private final Circle clip = new Circle(10);

    private final DoubleProperty pathAreaWidth = new SimpleDoubleProperty(this, "pathAreaWidth", 0.0);
    private final DoubleProperty pathAreaHeight = new SimpleDoubleProperty(this, "pathAreaHeight", 0.0);
    private final DoubleProperty zoomTranslateX = new SimpleDoubleProperty(this, "zoomTranslateX", 0.0);
    private final DoubleProperty zoomTranslateY = new SimpleDoubleProperty(this, "zoomTranslateY", 0.0);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    public OriginView() {
        outerCircle.setFill(Color.BLACK);
        middleCircle.setFill(Color.WHITE);
        innerCircle.setFill(Color.BLACK);
        setClip(clip);
        getChildren().addAll(outerCircle, middleCircle, innerCircle);
        translateXProperty().bind(pathAreaWidth.multiply(0.5).add(zoomTranslateX));  // ccx = paw*0.5 + ztx
        translateYProperty().bind(pathAreaHeight.multiply(0.5).add(zoomTranslateY)); // ccy = pah*0.5 + zty
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
}
