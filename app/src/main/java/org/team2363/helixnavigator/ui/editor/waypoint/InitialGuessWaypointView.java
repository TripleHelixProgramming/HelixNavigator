package org.team2363.helixnavigator.ui.editor.waypoint;

import org.team2363.helixnavigator.document.timeline.HInitialGuessPoint;

import javafx.scene.paint.Color;

public class InitialGuessWaypointView extends WaypointView {
    
    public InitialGuessWaypointView(HInitialGuessPoint initialGuessWaypoint) {
        super(initialGuessWaypoint);
        setWaypointFill(Color.SKYBLUE);
    }
}