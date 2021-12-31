package org.team2363.helixnavigator.document.waypoint;

import org.team2363.helixnavigator.document.HPathElement;

import java.util.regex.Pattern;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONString;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.TypeMarker;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class HWaypoint extends HPathElement {

    public static final Pattern VALID_WAYPOINT_NAME = Pattern.compile("[a-z0-9 _\\-]+", Pattern.CASE_INSENSITIVE);
    public static final int MAX_WAYPOINT_NAME_LENGTH = 50;
    
    private static final TypeMarker<HSoftWaypoint> softType = new TypeMarker<HSoftWaypoint>() {};
    private static final TypeMarker<HHardWaypoint> hardType = new TypeMarker<HHardWaypoint>() {};

    public static enum WaypointType {
        SOFT, HARD;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);

    protected HWaypoint() {
    }
    
    @Override
    public void translateRelativeX(double x) {
        setX(getX() + x);
    }
    
    @Override
    public void translateRelativeY(double y) {
        setY(getY() + y);
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

    public abstract WaypointType getWaypointType();

    public boolean isSoft() {
        return false;
    }

    public boolean isHard() {
        return false;
    }

    @DeserializedJSONDeterminer
    public static final TypeMarker<? extends HWaypoint> abstractDeterminer(JSONObject jsonEntry) throws JSONDeserializerException {
        if (!jsonEntry.containsKey("type")) {
            throw new JSONDeserializerException("Unable to determine which type of waypoint to deserialize to since the key \"type\" is missing in the JSONObject.");
        }
        String waypointTypeString = ((JSONString) jsonEntry.get("type")).getString();
        switch (waypointTypeString) {
            case "soft":
                return softType;
            case "hard":
                return hardType;
            default:
                throw new JSONDeserializerException("Unrecognized waypoint type string.");
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}