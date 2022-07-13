package org.team2363.helixnavigator.ui.editor.trajectory;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SegmentView {

    private final Line line = new Line();

    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);

    private final Pane pane = new Pane(line);

    public SegmentView(double startX, double startY, double endX, double endY) {

        line.startXProperty().bind(zoomScale.multiply(startX));
        line.startYProperty().bind(zoomScale.multiply(-startY));
        line.endXProperty().bind(zoomScale.multiply(endX));
        line.endYProperty().bind(zoomScale.multiply(-endY));

        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);
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