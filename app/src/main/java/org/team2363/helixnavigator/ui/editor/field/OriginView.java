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

    public OriginView() {
        outerCircle.setFill(Color.BLACK);
        middleCircle.setFill(Color.WHITE);
        innerCircle.setFill(Color.BLACK);
        setClip(clip);
        getChildren().addAll(outerCircle, middleCircle, innerCircle);
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
