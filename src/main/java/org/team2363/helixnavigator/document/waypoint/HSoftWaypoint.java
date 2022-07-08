package org.team2363.helixnavigator.document.waypoint;

import org.team2363.helixtrajectory.InitialGuessPoint;
import org.team2363.helixtrajectory.Waypoint;

import com.jlbabilino.json.DeserializedJSONConstructor;

public class HSoftWaypoint extends HWaypoint {
    
    @DeserializedJSONConstructor
    public HSoftWaypoint() {
    }

    @Override
    public WaypointType getWaypointType() {
        return WaypointType.SOFT;
    }

    @Override
    public boolean isSoft() {
        return true;
    }

    public Waypoint toWaypoint(InitialGuessPoint[] initialGuessPoints) {
        return new Waypoint(getX(), getY(), 0.0, 0.0, 0.0, 0.0, true, true, false, false, false, false, false, initialGuessPoints);
    }
}