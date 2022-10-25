package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.timeline.waypoint.HHolonomicWaypoint;

import javafx.scene.paint.Color;

public class CustomWaypointView extends WaypointView {
    
    public CustomWaypointView(HHolonomicWaypoint customWaypoint) {
        super(customWaypoint);
        setWaypointFill(Color.GREEN);
    }
}