package org.team2363.helixnavigator.document.timeline.waypoint;

import org.team2363.helixnavigator.document.compiled.HHolonomicTrajectory;
import org.team2363.helixnavigator.document.timeline.HTimelineElement;
import org.team2363.helixnavigator.document.timeline.HTimelineElement.TimelineElementType;
import org.team2363.helixtrajectory.HolonomicWaypoint;
import org.team2363.helixtrajectory.InitialGuessPoint;

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

public class HHolonomicWaypoint extends HWaypoint {

    public static enum VelocityConstraintType {
        NONE, MAGNITUDE, DIRECTION, COMPONENT, VECTOR, STATIC
    }

    private final DoubleProperty velocityX = new SimpleDoubleProperty(this, "velocityX", 0.0);
    private final DoubleProperty velocityY = new SimpleDoubleProperty(this, "velocityY", 0.0);
    private final DoubleProperty angularVelocity = new SimpleDoubleProperty(this, "angularVelocity", 0.0);

    private final BooleanProperty velocityXConstrained = new SimpleBooleanProperty(this, "velocityXConstrained", true);
    private final BooleanProperty velocityYConstrained = new SimpleBooleanProperty(this, "velocityYConstrained", true);
    private final BooleanProperty velocityMagnitudeConstrained = new SimpleBooleanProperty(this, "velocityMagnitudeConstrained", true);
    private final BooleanProperty angularVelocityConstrained = new SimpleBooleanProperty(this, "angularVelocityConstrained", true);
    private final ReadOnlyObjectWrapper<VelocityConstraintType> velocityConstraintType = new ReadOnlyObjectWrapper<>(this, "velocityConstraintType", VelocityConstraintType.STATIC);

    private final ReadOnlyObjectWrapper<HHolonomicTrajectory> holonomicTrajectorySegment = new ReadOnlyObjectWrapper<>(this, "holonomicTrajectorySegment", null);

    @DeserializedJSONConstructor
    public HHolonomicWaypoint() {
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
                if (getVelocityX() == 0.0 && getVelocityY() == 0.0) {
                    setVelocityConstraintType(VelocityConstraintType.STATIC);
                } else {
                    setVelocityConstraintType(VelocityConstraintType.DIRECTION);
                }
            } else {
                setVelocityConstraintType(VelocityConstraintType.NONE);
            }
        }
    }

    @Override
    public boolean isVelocityStateKnown() {
        return (isVelocityMagnitudeConstrained() && isVelocityXConstrained() && isVelocityYConstrained()) || 
                ((getVelocityX() == 0.0 && getVelocityY() == 0.0) && (
                (!isVelocityMagnitudeConstrained() && isVelocityXConstrained() && isVelocityYConstrained()) ||
                (isVelocityMagnitudeConstrained() && !isVelocityXConstrained() && !isVelocityYConstrained())));
    }

    @Override
    public TimelineElementType getTimelineElementType() {
        return TimelineElementType.HOLONOMIC_WAYPOINT;
    }
    @Override
    public boolean isHolonomicWaypoint() {
        return true;
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

    public HolonomicWaypoint toWaypoint(InitialGuessPoint[] initialGuessPoints) {
        return new HolonomicWaypoint(getX(), getY(), getHeading(), getVelocityX(), getVelocityY(), getAngularVelocity(),
                            isXConstrained(), isYConstrained(), isHeadingConstrained(), isVelocityXConstrained(),
                            isVelocityYConstrained(), isVelocityMagnitudeConstrained(), isAngularVelocityConstrained(), initialGuessPoints);
    }

    public static HHolonomicWaypoint positionHolonomicWaypoint() {
        HHolonomicWaypoint holonomicWaypoint = new HHolonomicWaypoint();
        holonomicWaypoint.setXConstrained(true);
        holonomicWaypoint.setYConstrained(true);
        holonomicWaypoint.setHeadingConstrained(false);
        holonomicWaypoint.setVelocityXConstrained(false);
        holonomicWaypoint.setVelocityYConstrained(false);
        holonomicWaypoint.setVelocityMagnitudeConstrained(false);
        holonomicWaypoint.setAngularVelocityConstrained(false);
        return holonomicWaypoint;
    }

    public static HHolonomicWaypoint headingHolonomicWaypoint() {
        HHolonomicWaypoint holonomicWaypoint = new HHolonomicWaypoint();
        holonomicWaypoint.setXConstrained(true);
        holonomicWaypoint.setYConstrained(true);
        holonomicWaypoint.setHeadingConstrained(true);
        holonomicWaypoint.setVelocityXConstrained(false);
        holonomicWaypoint.setVelocityYConstrained(false);
        holonomicWaypoint.setVelocityMagnitudeConstrained(false);
        holonomicWaypoint.setAngularVelocityConstrained(false);
        return holonomicWaypoint;
    }

    public static HHolonomicWaypoint staticHolonomicWaypoint() {
        HHolonomicWaypoint holonomicWaypoint = new HHolonomicWaypoint();
        holonomicWaypoint.setXConstrained(true);
        holonomicWaypoint.setYConstrained(true);
        holonomicWaypoint.setHeadingConstrained(true);
        holonomicWaypoint.setVelocityXConstrained(true);
        holonomicWaypoint.setVelocityYConstrained(true);
        holonomicWaypoint.setVelocityMagnitudeConstrained(true);
        holonomicWaypoint.setAngularVelocityConstrained(true);
        return holonomicWaypoint;
    }
}