package org.team2363.helixnavigator.ui.editor.field;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class OriginView {
    
    private final Line xLine = new Line(0, 0, 16, 0);
    private final Line xArrowLine1 = new Line(16, 0, 13, -3);
    private final Line xArrowLine2 = new Line(16, 0, 13, 3);
    private final Line yLine = new Line(0, 0, 0, -16);
    private final Line yArrowLine1 = new Line(0, -16, -3, -13);
    private final Line yArrowLine2 = new Line(0, -16, 3, -13);
    private final Circle outerCircle = new Circle(7);
    private final Circle middleCircle = new Circle(5);
    private final Circle innerCircle = new Circle(3);

    private final Pane pane = new Pane(xLine, xArrowLine1, xArrowLine2, yLine, yArrowLine1, yArrowLine2, outerCircle, middleCircle, innerCircle);

    private final BooleanProperty enable = new SimpleBooleanProperty(this, "enable", true);

    public OriginView() {
        xLine.setStrokeWidth(3);
        xArrowLine1.setStrokeWidth(3);
        xArrowLine2.setStrokeWidth(3);
        yLine.setStrokeWidth(3);
        yArrowLine1.setStrokeWidth(3);
        yArrowLine2.setStrokeWidth(3);
        xLine.setStrokeLineCap(StrokeLineCap.ROUND);
        xArrowLine1.setStrokeLineCap(StrokeLineCap.ROUND);
        xArrowLine2.setStrokeLineCap(StrokeLineCap.ROUND);
        yLine.setStrokeLineCap(StrokeLineCap.ROUND);
        yArrowLine1.setStrokeLineCap(StrokeLineCap.ROUND);
        yArrowLine2.setStrokeLineCap(StrokeLineCap.ROUND);
        outerCircle.setFill(Color.BLACK);
        middleCircle.setFill(Color.WHITE);
        innerCircle.setFill(Color.BLACK);

        enable.addListener((obs, wasEnable, isEnable) -> {
            pane.setOpacity(isEnable ? 1 : 0);
        });
    }

    public Pane getView() {
        return pane;
    }

    public final BooleanProperty enableProperty() {
        return enable;
    }

    public final void setEnable(boolean value) {
        enable.set(value);
    }

    public final boolean isEnable() {
        return enable.get();
    }
}