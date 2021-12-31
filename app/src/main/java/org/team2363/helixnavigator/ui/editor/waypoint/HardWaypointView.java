package org.team2363.helixnavigator.ui.editor.waypoint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HardWaypointView extends WaypointView {

    private final Rectangle cross1 = new Rectangle(11, 3);
    private final Rectangle cross2 = new Rectangle(11, 3);
    public HardWaypointView() {
        cross1.setRotate(45);
        cross2.setRotate(-45);
        cross1.setFill(Color.RED);
        cross2.setFill(Color.RED);
        getChildren().addAll(cross1, cross2);
    }
}