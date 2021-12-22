package org.team2363.helixnavigator.document.waypoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.SerializedJSONObjectValue;

public class HSoftWaypoint extends HWaypoint {

    @DeserializedJSONConstructor
    public HSoftWaypoint() {
    }

    @SerializedJSONObjectValue(key = "type")
    public WaypointType getWaypointType() {
        return WaypointType.SOFT;
    }
}
