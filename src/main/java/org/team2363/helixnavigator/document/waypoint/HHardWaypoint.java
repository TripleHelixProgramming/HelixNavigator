package org.team2363.helixnavigator.document.waypoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HHardWaypoint extends HWaypoint {
    
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);

    @DeserializedJSONConstructor
    public HHardWaypoint() {
    }

    @Override
    public WaypointType getWaypointType() {
        return WaypointType.HARD;
    }

    @Override
    public boolean isHard() {
        return true;
    }

    public final DoubleProperty headingProperty() {
        return heading;
    }

    @DeserializedJSONTarget
    public final void setHeading(@DeserializedJSONObjectValue(key = "heading") double value) {
        heading.set(value);
    }

    @SerializedJSONObjectValue(key = "heading")
    public final double getHeading() {
        return heading.get();
    }
}