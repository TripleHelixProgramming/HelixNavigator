package org.team2363.helixnavigator.document.waypoint;

import org.team2363.helixtrajectory.InitialGuessPoint;

import com.jlbabilino.json.DeserializedJSONConstructor;

public class HInitialGuessWaypoint extends HWaypoint {
    
    @DeserializedJSONConstructor
    public HInitialGuessWaypoint() {
    }

    @Override
    public WaypointType getWaypointType() {
        return WaypointType.INITIAL_GUESS;
    }

    @Override
    public boolean isInitialGuess() {
        return true;
    }

    public InitialGuessPoint toInitialGuessPoint() {
        return new InitialGuessPoint(getX(), getY(), 0.0);
    }
}