package org.team2363.helixnavigator.document.waypoint;

import org.team2363.helixtrajectory.Waypoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HCustomWaypoint extends HWaypoint {
    
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);
    private final DoubleProperty velocityX = new SimpleDoubleProperty(this, "velocityX", 0.0);
    private final DoubleProperty velocityY = new SimpleDoubleProperty(this, "velocityY", 0.0);
    private final DoubleProperty angularVelocity = new SimpleDoubleProperty(this, "angularVelocity", 0.0);

    private final BooleanProperty xConstrained = new SimpleBooleanProperty(this, "xConstrained", true);
    private final BooleanProperty yConstrained = new SimpleBooleanProperty(this, "yConstrained", true);
    private final BooleanProperty headingConstrained = new SimpleBooleanProperty(this, "headingConstrained", true);
    private final BooleanProperty velocityXConstrained = new SimpleBooleanProperty(this, "velocityXConstrained", false);
    private final BooleanProperty velocityYConstrained = new SimpleBooleanProperty(this, "velocityYConstrained", false);
    private final BooleanProperty angularVelocityConstrained = new SimpleBooleanProperty(this, "angularVelocityConstrained", false);

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

    public final BooleanProperty xConstrainedProperty() {
        return xConstrained;
    }
    @DeserializedJSONTarget
    public final void setXConstrained(@DeserializedJSONObjectValue(key = "x_constrained") boolean value) {
        xConstrained.set(value);
    }
    @SerializedJSONObjectValue(key = "x_constrained")
    public final boolean isXConstrained() {
        return xConstrained.get();
    }

    public final BooleanProperty yConstrainedProperty() {
        return yConstrained;
    }
    @DeserializedJSONTarget
    public final void setYConstrained(@DeserializedJSONObjectValue(key = "y_constrained") boolean value) {
        yConstrained.set(value);
    }
    @SerializedJSONObjectValue(key = "y_constrained")
    public final boolean isYConstrained() {
        return yConstrained.get();
    }

    public final BooleanProperty headingConstrainedProperty() {
        return headingConstrained;
    }
    @DeserializedJSONTarget
    public final void setHeadingConstrained(@DeserializedJSONObjectValue(key = "heading_constrained") boolean value) {
        headingConstrained.set(value);
    }
    @SerializedJSONObjectValue(key = "heading_constrained")
    public final boolean isHeadingConstrained() {
        return headingConstrained.get();
    }

    public final BooleanProperty velocityXConstrainedProperty() {
        return velocityXConstrained;
    }
    @DeserializedJSONTarget
    public final void setVelocityXConstrained(@DeserializedJSONObjectValue(key = "velocity_x_constrained") boolean value) {
        velocityXConstrained.set(value);
    }
    @SerializedJSONObjectValue(key = "velocity_x_constrained")
    public final boolean isVelocityXConstrained() {
        return velocityXConstrained.get();
    }

    public final BooleanProperty velocityYConstrainedProperty() {
        return velocityYConstrained;
    }
    @DeserializedJSONTarget
    public final void setVelocityYConstrained(@DeserializedJSONObjectValue(key = "velocity_y_constrained") boolean value) {
        velocityYConstrained.set(value);
    }
    @SerializedJSONObjectValue(key = "velocity_y_constrained")
    public final boolean isVelocityYConstrained() {
        return velocityYConstrained.get();
    }

    public final BooleanProperty angularVelocityConstrainedProperty() {
        return angularVelocityConstrained;
    }
    @DeserializedJSONTarget
    public final void setAngularVelocityConstrained(@DeserializedJSONObjectValue(key = "angular_velocity_constrained") boolean value) {
        angularVelocityConstrained.set(value);
    }
    @SerializedJSONObjectValue(key = "angular_velocity_constrained")
    public final boolean isAngularVelocityConstrained() {
        return angularVelocityConstrained.get();
    }

    @Override
    public Waypoint toWaypoint() {
        return new Waypoint(getX(), getY(), getHeading(), getVelocityX(), getVelocityY(), getAngularVelocity(),
                            isXConstrained(), isYConstrained(), isHeadingConstrained(), isVelocityXConstrained(),
                            isVelocityYConstrained(), isAngularVelocityConstrained());
    }
}