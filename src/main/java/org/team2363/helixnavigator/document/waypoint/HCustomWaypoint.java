package org.team2363.helixnavigator.document.waypoint;

import org.team2363.helixtrajectory.Waypoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HCustomWaypoint extends HWaypoint {
    
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);
    private final DoubleProperty velocityX = new SimpleDoubleProperty(this, "velocityX", 0.0);
    private final DoubleProperty velocityY = new SimpleDoubleProperty(this, "velocityY", 0.0);
    private final DoubleProperty angularVelocity = new SimpleDoubleProperty(this, "angularVelocity", 0.0);

    @DeserializedJSONConstructor
    public HCustomWaypoint() {
    }

    @Override
    public WaypointType getWaypointType() {
        return WaypointType.CUSTOM;
    }

    @Override
    public boolean isCustom() {
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

    public final DoubleProperty velocityXProperty() {
        return velocityX;
    }
    @DeserializedJSONTarget
    public final void setVelocityX(@DeserializedJSONObjectValue(key = "velocity_x") double value) {
        velocityX.set(value);
    }
    @SerializedJSONObjectValue(key = "velocity_x")
    public final double getVelocityX() {
        return velocityX.get();
    }

    public final DoubleProperty velocityYProperty() {
        return velocityY;
    }
    @DeserializedJSONTarget
    public final void setVelocityY(@DeserializedJSONObjectValue(key = "velocity_y") double value) {
        velocityY.set(value);
    }
    @SerializedJSONObjectValue(key = "velocity_y")
    public final double getVelocityY() {
        return velocityY.get();
    }

    public final DoubleProperty angularVelocityProperty() {
        return angularVelocity;
    }
    @DeserializedJSONTarget
    public final void setAngularVelocity(@DeserializedJSONObjectValue(key = "angular_velocity") double value) {
        angularVelocity.set(value);
    }
    @SerializedJSONObjectValue(key = "angular_velocity")
    public final double getAngularVelocity() {
        return angularVelocity.get();
    }

    @Override
    public Waypoint toWaypoint() {
        return new Waypoint(getX(), getY(), getHeading());
    }
}