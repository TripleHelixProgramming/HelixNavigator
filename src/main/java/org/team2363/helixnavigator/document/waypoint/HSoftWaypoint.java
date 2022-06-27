package org.team2363.helixnavigator.document.waypoint;

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

    @Override
    public Waypoint toWaypoint() {
        return new Waypoint(getX(), getY(), 0.0);
    }
}