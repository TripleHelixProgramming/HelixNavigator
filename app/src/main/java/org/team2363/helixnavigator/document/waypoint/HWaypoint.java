package org.team2363.helixnavigator.document.waypoint;

import java.util.regex.Pattern;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.SerializedJSONObjectValue;

import org.team2363.helixnavigator.document.HPathElement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;

public class HWaypoint extends HPathElement {

    public static final Pattern VALID_WAYPOINT_NAME = Pattern.compile("[a-z0-9 _\\-]+", Pattern.CASE_INSENSITIVE);
    public static final int MAX_WAYPOINT_NAME_LENGTH = 50;

    public static enum WaypointType {
        SOFT, HARD;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final ObjectProperty<WaypointType> waypointType = new SimpleObjectProperty<>(this, "waypoint", WaypointType.SOFT);
    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "x", 0.0);

    @DeserializedJSONConstructor
    public HWaypoint() {
    }

    public HWaypoint(WaypointType waypointType) {
        setWaypointType(waypointType);
    }

    public boolean isSoft() {
        return getWaypointType() == WaypointType.SOFT;
    }

    public boolean isHard() {
        return getWaypointType() == WaypointType.HARD;
    }

    @Override
    public void translateRelativeX(double x) {
        setX(getX() + x);
    }

    @Override
    public void translateRelativeY(double y) {
        setY(getY() + y);
    }

    public void handleMouseDragged(MouseEvent event) {
        System.out.print("dragging: x: ");
        System.out.print(event.getX() + " y: ");
        System.out.println(event.getY());
        setX(event.getX());
        setY(event.getY());
        event.consume();
    }

    public final ObjectProperty<WaypointType> waypointTypeProperty() {
        return waypointType;
    }

    public final void setWaypointType(WaypointType value) {
        waypointType.set(value);
    }

    @DeserializedJSONTarget
    public final void setWaypointType(@DeserializedJSONObjectValue(key = "waypoint_type") String typeString) throws JSONDeserializerException {
        switch (typeString.trim().toLowerCase()) {
            case "soft":
                setWaypointType(WaypointType.SOFT);
                break;
            case "hard":
                setWaypointType(WaypointType.HARD);
                break;
            default:
                throw new JSONDeserializerException("Invalid waypoint type string \"" + typeString + "\"");
        }
    }

    @SerializedJSONObjectValue(key = "waypoint_type")
    public final WaypointType getWaypointType() {
        return waypointType.get();
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
}