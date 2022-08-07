package org.team2363.helixnavigator.document.obstacle;

import org.team2363.helixnavigator.document.HPathElement;
import org.team2363.helixtrajectory.Obstacle;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONString;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class HObstacle extends HPathElement {

    @JSONSerializable(JSONType.STRING)
    @JSONDeserializable({JSONType.STRING})
    public static enum ObstacleType {
        CIRCLE,
        POLYGON,
        RECTANGLE;

        @DeserializedJSONConstructor
        public static ObstacleType forName(@DeserializedJSONEntry String name) {
            return valueOf(name.toUpperCase());
        }

        @SerializedJSONEntry
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final DoubleProperty safetyDistance = new SimpleDoubleProperty(this, "safetyDistance", 0.0);

    protected HObstacle() {
    }

    public abstract Obstacle toObstacle();

    public final DoubleProperty safetyDistanceProperty() {
        return safetyDistance;
    }

    @DeserializedJSONTarget
    public final void setSafetyDistance(@DeserializedJSONObjectValue(key = "safety_distance") double value) {
        safetyDistance.set(value);
    }

    @SerializedJSONObjectValue(key = "safety_distance")
    public final double getSafetyDistance() {
        return safetyDistance.get();
    }

    @SerializedJSONObjectValue(key = "obstacle_type")
    public abstract ObstacleType getObstacleType();

    public boolean isCircle() {
        return false;
    }

    public boolean isPolygon() {
        return false;
    }

    public boolean isRectangle() {
        return false;
    }

    @DeserializedJSONDeterminer
    public static final Class<? extends HObstacle> determiner(@DeserializedJSONEntry JSONObject jsonEntry) throws JSONDeserializerException {
        if (!jsonEntry.containsKey("obstacle_type")) {
            throw new JSONDeserializerException("Unable to determine which type of obstacle to deserialize to since the key \"obstacle_type\" is missing in the JSONObject.");
        }
        String typeString = ((JSONString) jsonEntry.get("obstacle_type")).getString();
        switch (typeString.trim().toLowerCase()) {
            case "circle":
                return HCircleObstacle.class;
            case "polygon":
                return HPolygonObstacle.class;
            case "rectangle":
                return HRectangleObstacle.class;
            default:
                throw new JSONDeserializerException("Unrecognized obstacle type string: \"" + typeString + "\"");
        }
    }
}
