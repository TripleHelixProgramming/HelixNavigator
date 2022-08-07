package org.team2363.helixnavigator.document.timeline;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.transform.Transform;

public abstract class HWaypoint extends HTimelineElement {

    @JSONSerializable(JSONType.STRING)
    @JSONDeserializable({JSONType.STRING})
    public static enum WaypointType {
        HOLONOMIC;

        @DeserializedJSONConstructor
        public static WaypointType forName(@DeserializedJSONEntry String name) {
            return valueOf(name);
        }

        @SerializedJSONEntry
        public String toString() {
            return name().toLowerCase();
        }
    }

    public static enum PositionConstraintType {
        ERROR, X, X_AND_Y, Y;
    }

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);

    private final BooleanProperty xConstrained = new SimpleBooleanProperty(this, "xConstrained", true);
    private final BooleanProperty yConstrained = new SimpleBooleanProperty(this, "yConstrained", true);
    private final BooleanProperty headingConstrained = new SimpleBooleanProperty(this, "headingConstrained", true);

    private final ReadOnlyObjectWrapper<PositionConstraintType> positionConstraintType = new ReadOnlyObjectWrapper<>(this, "positionConstraintType", PositionConstraintType.X_AND_Y);

    protected HWaypoint() {
        ChangeListener<? super Boolean> onPositionConstraintChanged = (obsVal, wasOn, isOn) -> updatePositionConstraintType();
        xConstrained.addListener(onPositionConstraintChanged);
        yConstrained.addListener(onPositionConstraintChanged);
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

    @Override
    public void translateRelativeX(double dx) {
        setX(getX() + dx);
    }
    @Override
    public void translateRelativeY(double dy) {
        setY(getY() + dy);
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
    public TimelineElementType getTimelineElementType() {
        return TimelineElementType.WAYPOINT;
    }
    @Override
    public boolean isWaypoint() {
        return true;
    }

    @SerializedJSONObjectValue(key = "waypoint_type")
    public abstract WaypointType getWaypointType();
    public boolean isHolonomicWaypoint() {
        return false;
    }

    public boolean isPositionStateKnown() {
        return isXConstrained() || isYConstrained();
    }
    public abstract boolean isVelocityStateKnown();
    public boolean isStateKnown() {
        return isPositionStateKnown() && isVelocityStateKnown();
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

    @DeserializedJSONDeterminer
    public static Class<? extends HWaypoint> waypointDeterminer(
            @DeserializedJSONObjectValue(key = "waypoint_type") WaypointType waypointType) throws JSONDeserializerException {
        switch (waypointType) {
            case HOLONOMIC:
                return HHolonomicWaypoint.class;
            default:
                throw new JSONDeserializerException("Waypoint type cannot be null");
        }
    }
}