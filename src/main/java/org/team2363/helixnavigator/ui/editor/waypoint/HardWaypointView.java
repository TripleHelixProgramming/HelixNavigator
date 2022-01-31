package org.team2363.helixnavigator.ui.editor.waypoint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class HardWaypointView extends WaypointView {

    private final Line cross1 = new Line(-4, 4, 4, -4);
    private final Line cross2 = new Line(-4, -4, 4, 4);

    public HardWaypointView() {
        cross1.setStrokeWidth(3);
        cross2.setStrokeWidth(3);
        cross1.setStroke(Color.RED);
        cross2.setStroke(Color.RED);

        getView().getChildren().addAll(cross1, cross2);
    }
}