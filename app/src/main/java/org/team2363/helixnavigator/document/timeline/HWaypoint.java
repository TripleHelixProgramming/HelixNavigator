package org.team2363.helixnavigator.document.timeline;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONString;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.transform.Transform;

public abstract class HWaypoint extends HTimelineElement {

    public static enum WaypointType {
        SOFT, HARD, CUSTOM, INITIAL_GUESS;

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
    public void transformRelative(Transform transform) {
        Point2D newPoint = transform.transform(getX(), getY());
        setX(newPoint.getX());
        setY(newPoint.getY());
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

    public boolean isCustom() {
        return false;
    }

    public boolean isInitialGuess() {
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

    @DeserializedJSONDeterminer
    public static Class<? extends HWaypoint> determiner(@DeserializedJSONEntry JSONObject jsonObject) throws JSONDeserializerException {
        if (!jsonObject.containsKey("waypoint_type")) {
            throw new JSONDeserializerException("Missing \"waypoint_type\" key");
        }
        String typeString = ((JSONString) jsonObject.get("waypoint_type")).getString();
        switch (typeString.trim().toLowerCase()) {
            case "soft":
                return HSoftWaypoint.class;
            case "hard":
                return HHardWaypoint.class;
            case "custom":
                return HCustomWaypoint.class;
            case "initial_guess":
                return HInitialGuessPoint.class;
            default:
                throw new JSONDeserializerException("Unrecognized waypoint type: \"" + typeString + "\"");
        }
    }
}