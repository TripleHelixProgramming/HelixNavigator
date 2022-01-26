package org.team2363.helixnavigator.ui.editor.line;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineView {

    private final Line bottomLine = new Line();
    private final Line topLine = new Line();
    
    private final Line clip = new Line();

    private final DoubleProperty startPointX = new SimpleDoubleProperty(this, "startPointX", 0.0);
    private final DoubleProperty startPointY = new SimpleDoubleProperty(this, "startPointY", 0.0);
    private final DoubleProperty endPointX = new SimpleDoubleProperty(this, "endPointX", 0.0);
    private final DoubleProperty endPointY = new SimpleDoubleProperty(this, "endPointY", 0.0);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    private final Pane pane = new Pane(bottomLine, topLine);

    public LineView() {
        bottomLine.startXProperty().bind(startPointX.multiply(zoomScale));
        bottomLine.startYProperty().bind(startPointY.multiply(zoomScale).negate());
        bottomLine.endXProperty().bind(endPointX.multiply(zoomScale));
        bottomLine.endYProperty().bind(endPointY.multiply(zoomScale).negate());

        topLine.startXProperty().bind(startPointX.multiply(zoomScale));
        topLine.startYProperty().bind(startPointY.multiply(zoomScale).negate());
        topLine.endXProperty().bind(endPointX.multiply(zoomScale));
        topLine.endYProperty().bind(endPointY.multiply(zoomScale).negate());

        clip.startXProperty().bind(startPointX.multiply(zoomScale));
        clip.startYProperty().bind(startPointY.multiply(zoomScale).negate());
        clip.endXProperty().bind(endPointX.multiply(zoomScale));
        clip.endYProperty().bind(endPointY.multiply(zoomScale).negate());

        bottomLine.setStrokeWidth(5);
        bottomLine.setStroke(Color.gray(0.6));
        topLine.setStrokeWidth(3);
        topLine.setStroke(Color.BLACK);
        clip.setStrokeWidth(5);
        
        pane.setClip(clip);
    }

    public final DoubleProperty startPointXProperty() {
        return startPointX;
    }

    public final void setStartPointX(double value) {
        startPointX.set(value);
    }

    public final double getStartPointX() {
        return startPointX.get();
    }

    public final DoubleProperty startPointYProperty() {
        return startPointY;
    }

    public final void setStartPointY(double value) {
        startPointY.set(value);
    }

    public final double getStartPointY() {
        return startPointY.get();
    }

    public final DoubleProperty endPointXProperty() {
        return endPointX;
    }
    
    public final void setEndPointX(double value) {
        endPointX.set(value);
    }
    
    public final double getEndPointX() {
        return endPointX.get();
    }
    
    public final DoubleProperty endPointYProperty() {
        return endPointY;
    }
    
    public final void setEndPointY(double value) {
        endPointY.set(value);
    }
    
    public final double getEndPointY() {
        return endPointY.get();
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

    public Pane getView() {
        return pane;
    }
}