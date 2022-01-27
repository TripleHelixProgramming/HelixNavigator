package org.team2363.helixnavigator.ui.editor.field;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OriginView {
    
    private final Circle outerCircle = new Circle(7);
    private final Circle middleCircle = new Circle(5);
    private final Circle innerCircle = new Circle(3);

    private final Circle clip = new Circle(10);

    private final Pane pane = new Pane(outerCircle, middleCircle, innerCircle);

    public OriginView() {
        outerCircle.setFill(Color.BLACK);
        middleCircle.setFill(Color.WHITE);
        innerCircle.setFill(Color.BLACK);
        pane.setClip(clip);
    }

    public Pane getView() {
        return pane;
    }
}
