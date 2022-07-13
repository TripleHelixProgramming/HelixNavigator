package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.waypoint.HInitialGuessWaypoint;

import javafx.scene.paint.Color;

public class InitialGuessWaypointView extends WaypointView {
    
    public InitialGuessWaypointView(HInitialGuessWaypoint initialGuessWaypoint) {
        super(initialGuessWaypoint);
        setWaypointFill(Color.SKYBLUE);
    }
}