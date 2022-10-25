package org.team2363.helixnavigator.document.waypoint;

import java.util.List;

import org.team2363.helixtrajectory.HolonomicWaypoint;
import org.team2363.helixtrajectory.InitialGuessPoint;

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

    public HolonomicWaypoint toWaypoint(List<InitialGuessPoint> initialGuessPoints) {
        return new HolonomicWaypoint(getX(), getY(), 0.0, 0.0, 0.0, 0.0, true, true, false, false, false, false, false, 100, initialGuessPoints, List.of());
    }
}