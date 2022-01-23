package org.team2363.helixnavigator.ui.editor.line;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineView extends Pane {

    private final Line bottomLine = new Line();
    private final Line topLine = new Line();

    private final DoubleProperty initialPointX = new SimpleDoubleProperty(this, "initialPointX", 0.0);
    private final DoubleProperty initialPointY = new SimpleDoubleProperty(this, "initialPointY", 0.0);
    private final DoubleProperty finalPointX = new SimpleDoubleProperty(this, "finalPointX", 0.0);
    private final DoubleProperty finalPointY = new SimpleDoubleProperty(this, "finalPointY", 0.0);
    private final DoubleProperty pathAreaWidth = new SimpleDoubleProperty(this, "pathAreaWidth", 0.0);
    private final DoubleProperty pathAreaHeight = new SimpleDoubleProperty(this, "pathAreaHeight", 0.0);
    private final DoubleProperty zoomTranslateX = new SimpleDoubleProperty(this, "zoomTranslateX", 0.0);
    private final DoubleProperty zoomTranslateY = new SimpleDoubleProperty(this, "zoomTranslateY", 0.0);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    public LineView() {
        bottomLine.startXProperty().bind(pathAreaWidth.multiply(0.5).add(zoomTranslateX).add(initialPointX.multiply(zoomScale)));  // sx = 0.5*paw + ztx + ipx*zs
        bottomLine.startYProperty().bind(pathAreaHeight.multiply(0.5).add(zoomTranslateY).subtract(initialPointY.multiply(zoomScale))); // sy = 0.5*pah + zty + ipy*zs
        bottomLine.endXProperty().bind(pathAreaWidth.multiply(0.5).add(zoomTranslateX).add(finalPointX.multiply(zoomScale)));  // sx = 0.5*paw + ztx + fpx*zs
        bottomLine.endYProperty().bind(pathAreaHeight.multiply(0.5).add(zoomTranslateY).subtract(finalPointY.multiply(zoomScale))); // sy = 0.5*pah + zty + fpy*zs

        topLine.startXProperty().bind(pathAreaWidth.multiply(0.5).add(zoomTranslateX).add(initialPointX.multiply(zoomScale)));  // sx = 0.5*paw + ztx + ipx*zs
        topLine.startYProperty().bind(pathAreaHeight.multiply(0.5).add(zoomTranslateY).subtract(initialPointY.multiply(zoomScale))); // sy = 0.5*pah + zty + ipy*zs
        topLine.endXProperty().bind(pathAreaWidth.multiply(0.5).add(zoomTranslateX).add(finalPointX.multiply(zoomScale)));  // sx = 0.5*paw + ztx + fpx*zs
        topLine.endYProperty().bind(pathAreaHeight.multiply(0.5).add(zoomTranslateY).subtract(finalPointY.multiply(zoomScale))); // sy = 0.5*pah + zty + fpy*zs

        bottomLine.setStrokeWidth(5);
        bottomLine.setStroke(Color.gray(0.6));
        topLine.setStrokeWidth(3);
        topLine.setStroke(Color.BLACK);

        getChildren().addAll(bottomLine, topLine);
    }

    public final DoubleProperty initialPointXProperty() {
        return initialPointX;
    }

    public final void setInitialPointX(double value) {
        initialPointX.set(value);
    }

    public final double getInitialPointX() {
        return initialPointX.get();
    }

    public final DoubleProperty initialPointYProperty() {
        return initialPointY;
    }

    public final void setInitialPointY(double value) {
        initialPointY.set(value);
    }

    public final double getInitialPointY() {
        return initialPointY.get();
    }

    public final DoubleProperty finalPointXProperty() {
        return finalPointX;
    }
    
    public final void setFinalPointX(double value) {
        finalPointX.set(value);
    }
    
    public final double getFinalPointX() {
        return finalPointX.get();
    }
    
    public final DoubleProperty finalPointYProperty() {
        return finalPointY;
    }
    
    public final void setFinalPointY(double value) {
        finalPointY.set(value);
    }
    
    public final double getFinalPointY() {
        return finalPointY.get();
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
