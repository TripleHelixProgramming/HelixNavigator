package com.team2363.helixnavigator.document.waypoint;

import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author Justin Babilino
 */
public class HHardWaypoint extends HAbstractWaypoint {

    /**
     * The double property for the heading of the robot when passing through this
     * waypoint
     */
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);

    /**
     * Constructs an <code>HHardWaypoint</code>. The x, y, and heading values are
     * set to <code>0.0</code>.
     */
    public HHardWaypoint() {
    }

    /**
     * @return the heading property
     */
    public final DoubleProperty headingPropery() {
        return heading;
    }

    /**
     * @param value the new heading value
     */
    public final void setHeading(double value) {
        heading.set(value);
    }

    /**
     * @return the current heading value
     */
    @SerializedJSONObjectValue(key = "heading")
    public final double getHeading() {
        return heading.get();
    }

    public WaypointType getWaypointType() {
        return WaypointType.HARD;
    }
}
