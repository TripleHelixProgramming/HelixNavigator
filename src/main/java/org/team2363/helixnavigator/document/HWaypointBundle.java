package org.team2363.helixnavigator.document;

import java.util.ArrayList;
import java.util.List;

import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.JSONEntry.JSONType;

import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

@JSONSerializable(rootType = JSONType.ARRAY)
public class HWaypointBundle {
    
    private List<double[]> bundleWaypoints = new ArrayList<>();

    public HWaypointBundle(HPath path) {
        for (HWaypoint waypoint : path.getWaypoints()) {
            if (waypoint.isHard()) {
                HHardWaypoint hardWaypoint = (HHardWaypoint) waypoint;
                double[] bundleWaypoint = {hardWaypoint.getX(), hardWaypoint.getY(), hardWaypoint.getHeading()};
                bundleWaypoints.add(bundleWaypoint);
            }
        }
    }

    @SerializedJSONEntry
    public List<double[]> getWaypointBundle() {
        return bundleWaypoints;
    }
}