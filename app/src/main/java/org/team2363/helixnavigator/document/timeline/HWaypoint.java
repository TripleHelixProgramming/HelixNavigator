package org.team2363.helixnavigator.document.timeline;

import org.team2363.helixtrajectory.InitialGuessPoint;
import org.team2363.helixtrajectory.Waypoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.transform.Transform;

public class HWaypoint extends HTimelineElement {

    public static enum PositionConstraintType {
        NONE, X, X_AND_Y, Y, ERROR
    }
    public static enum VelocityConstraintType {
        NONE, DIRECTION, MAGNITUDE, DIRECTION_AND_MAGNITUDE, X, Y, STATIC
    }

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);
    private final DoubleProperty velocityX = new SimpleDoubleProperty(this, "velocityX", 0.0);
    private final DoubleProperty velocityY = new SimpleDoubleProperty(this, "velocityY", 0.0);
    private final DoubleProperty angularVelocity = new SimpleDoubleProperty(this, "angularVelocity", 0.0);

    private final BooleanProperty xConstrained = new SimpleBooleanProperty(this, "xConstrained", true);
    private final BooleanProperty yConstrained = new SimpleBooleanProperty(this, "yConstrained", true);
    private final BooleanProperty headingConstrained = new SimpleBooleanProperty(this, "headingConstrained", true);
    private final BooleanProperty velocityXConstrained = new SimpleBooleanProperty(this, "velocityXConstrained", false);
    private final BooleanProperty velocityYConstrained = new SimpleBooleanProperty(this, "velocityYConstrained", false);
    private final BooleanProperty velocityMagnitudeConstrained = new SimpleBooleanProperty(this, "velocityMagnitudeConstrained", true);
    private final BooleanProperty angularVelocityConstrained = new SimpleBooleanProperty(this, "angularVelocityConstrained", true);

    private final ReadOnlyObjectWrapper<PositionConstraintType> positionConstraintType = new ReadOnlyObjectWrapper<>(this, "positionConstraintType", PositionConstraintType.X_AND_Y);
    private final ReadOnlyObjectWrapper<VelocityConstraintType> velocityConstraintType = new ReadOnlyObjectWrapper<>(this, "velocityConstraintType", VelocityConstraintType.STATIC);

    @DeserializedJSONConstructor
    public HWaypoint() {
        ChangeListener<? super Boolean> onPositionConstraintChanged = (obsVal, wasOn, isOn) -> updatePositionConstraintType();
        xConstrained.addListener(onPositionConstraintChanged);
        yConstrained.addListener(onPositionConstraintChanged);

        ChangeListener<? super Boolean> onVelocityConstraintChanged = (obsVal, wasOn, isOn) -> updateVelocityConstraintType();
        velocityXConstrained.addListener(onVelocityConstraintChanged);
        velocityYConstrained.addListener(onVelocityConstraintChanged);
        velocityMagnitudeConstrained.addListener(onVelocityConstraintChanged);
        velocityMagnitudeConstrained.addListener(onVelocityConstraintChanged);

        ChangeListener<? super Number> onVelocityComponentChanged = (obsVal, oldVal, newVal) -> {
            if (oldVal.doubleValue() == 0.0 || newVal.doubleValue() == 0.0) {
                updateVelocityConstraintType();
            }
        };
        velocityX.addListener(onVelocityComponentChanged);
        velocityY.addListener(onVelocityComponentChanged);
    }

    private void updatePositionConstraintType() {
        if (isXConstrained() && isYConstrained()) {
            setPositionConstraintType(PositionConstraintType.X_AND_Y);
        } else if (isXConstrained() && !isYConstrained()) {
            setPositionConstraintType(PositionConstraintType.X);
        } else if (!isXConstrained() && isYConstrained()) {
            setPositionConstraintType(PositionConstraintType.Y);
        } else { // should be at least one constraining
            setPositionConstraintType(PositionConstraintType.ERROR);
        }
    }

    private void updateVelocityConstraintType() {
        if (isVelocityMagnitudeConstrained()) {
            if (getVelocityX() == 0.0 && getVelocityY() == 0.0) {
                setVelocityConstraintType(VelocityConstraintType.STATIC);
            } else if (isVelocityXConstrained() && isVelocityYConstrained()) {
                setVelocityConstraintType(VelocityConstraintType.DIRECTION_AND_MAGNITUDE);
            } else if (!isVelocityXConstrained() && !isVelocityYConstrained()) {
                setVelocityConstraintType(VelocityConstraintType.MAGNITUDE);
            } else if (isVelocityXConstrained() && !isVelocityYConstrained()) {
                setVelocityConstraintType(VelocityConstraintType.X);
            } else /*if (!isVelocityXConstrained() && isVelocityYConstrained())*/ {
                setVelocityConstraintType(VelocityConstraintType.Y);
            }
        } else { // magnitude unconstrained
            if (isVelocityXConstrained() && isVelocityYConstrained()) {
                setVelocityConstraintType(VelocityConstraintType.DIRECTION);
            } else {
                setVelocityConstraintType(VelocityConstraintType.NONE);
            }
        }
    }

    @Override
    public void transformRelative(Transform transform) {
        Point2D newPoint = transform.transform(getX(), getY());
        setX(newPoint.getX());
        setY(newPoint.getY());
        double deltaAngle = Math.atan2(transform.getMyx(), transform.getMxx());
        setHeading(getHeading() + deltaAngle);
    }
    @Override
    public void translateRelativeX(double dx) {
        setX(getX() + dx);
    }
    @Override
    public void translateRelativeY(double dy) {
        setY(getY() + dy);
    }

    @Override
    public TimelineElementType getTimelineElementType() {
        return TimelineElementType.WAYPOINT;
    }
    @Override
    public boolean isWaypoint() {
        return true;
    }

    public final boolean isStateKnown() {
        return getPositionConstraintType() == PositionConstraintType.X_AND_Y &&
                (getVelocityConstraintType() == VelocityConstraintType.STATIC
                || getVelocityConstraintType() == VelocityConstraintType.DIRECTION_AND_MAGNITUDE);
    }

    public final ReadOnlyObjectProperty<PositionConstraintType> positionConstraintTypeProperty() {
        return positionConstraintType.getReadOnlyProperty();
    }
    private final void setPositionConstraintType(PositionConstraintType value) {
        positionConstraintType.set(value);
    }
    public final PositionConstraintType getPositionConstraintType() {
        return positionConstraintType.get();
    }

    public final ReadOnlyObjectProperty<VelocityConstraintType> velocityConstraintTypeProperty() {
        return velocityConstraintType.getReadOnlyProperty();
    }
    private final void setVelocityConstraintType(VelocityConstraintType value) {
        velocityConstraintType.set(value);
    }
    public final VelocityConstraintType getVelocityConstraintType() {
        return velocityConstraintType.get();
    }

    public final DoubleProperty xProperty() {
        return x;
    }
    @DeserializedJSONTarget
    public final void setX(@DeserializedJSONObjectValue(key = "x") double value) {
        x.set(value);
    }
    @SerializedJSONObjectValue(key = "x")
    public final double getX() {
        return x.get();
    }

    public final DoubleProperty yProperty() {
        return y;
    }
    @DeserializedJSONTarget
    public final void setY(@DeserializedJSONObjectValue(key = "y") double value) {
        y.set(value);
    }
    @SerializedJSONObjectValue(key = "y")
    public final double getY() {
        return y.get();
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

    public final BooleanProperty velocityMagnitudeConstrainedProperty() {
        return velocityMagnitudeConstrained;
    }
    @DeserializedJSONTarget
    public final void setVelocityMagnitudeConstrained(@DeserializedJSONObjectValue(key = "velocity_magnitude_constrained") boolean value) {
        velocityMagnitudeConstrained.set(value);
    }
    @SerializedJSONObjectValue(key = "velocity_magnitude_constrained")
    public final boolean isVelocityMagnitudeConstrained() {
        return velocityMagnitudeConstrained.get();
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

    public Waypoint toWaypoint(InitialGuessPoint[] initialGuessPoints) {
        return new Waypoint(getX(), getY(), getHeading(), getVelocityX(), getVelocityY(), getAngularVelocity(),
                            isXConstrained(), isYConstrained(), isHeadingConstrained(), isVelocityXConstrained(),
                            isVelocityYConstrained(), isVelocityMagnitudeConstrained(), isAngularVelocityConstrained(), initialGuessPoints);
    }
}