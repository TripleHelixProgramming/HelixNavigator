package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.waypoint.HCustomWaypoint;

import javafx.scene.paint.Color;

public class CustomWaypointView extends WaypointView {
    
    public CustomWaypointView(HCustomWaypoint customWaypoint) {
        super(customWaypoint);
        setWaypointFill(Color.GREEN);
    }
}