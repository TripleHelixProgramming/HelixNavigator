package org.team2363.helixnavigator.document.waypoint;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONString;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.TypeMarker;

import org.team2363.helixnavigator.document.HPathElement;
import org.team2363.helixtrajectory.Waypoint;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class HWaypoint extends HPathElement {

    private static final TypeMarker<HSoftWaypoint> SOFT_TYPE = new TypeMarker<HSoftWaypoint>() {};
    private static final TypeMarker<HHardWaypoint> HARD_TYPE = new TypeMarker<HHardWaypoint>() {};

    public static enum WaypointType {
        SOFT, HARD;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);

    public HWaypoint() {
    }

    @Override
    public void translateRelativeX(double dx) {
        setX(getX() + dx);
    }

    @Override
    public void translateRelativeY(double dy) {
        setY(getY() + dy);
    }

    @SerializedJSONObjectValue(key = "waypoint_type")
    public abstract WaypointType getWaypointType();

    public boolean isSoft() {
        return false;
    }

    public boolean isHard() {
        return false;
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

    public abstract Waypoint toWaypoint();

    @DeserializedJSONDeterminer
    public static TypeMarker<? extends HWaypoint> determiner(JSONObject jsonObject) throws JSONDeserializerException {
        if (!jsonObject.containsKey("waypoint_type")) {
            throw new JSONDeserializerException("Missing \"waypoint_type\" key");
        }
        String typeString = ((JSONString) jsonObject.get("waypoint_type")).getString();
        switch (typeString.trim().toLowerCase()) {
            case "soft":
                return SOFT_TYPE;
            case "hard":
                return HARD_TYPE;
            default:
                throw new JSONDeserializerException("Unrecognized waypoint type: \"" + typeString + "\"");
        }
    }
}