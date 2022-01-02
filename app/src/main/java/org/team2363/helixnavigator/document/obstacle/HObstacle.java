package org.team2363.helixnavigator.document.obstacle;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONString;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.TypeMarker;

import org.team2363.helixnavigator.document.HPathElement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class HObstacle extends HPathElement {

    private static final TypeMarker<HPolygonObstacle> polygonType = new TypeMarker<HPolygonObstacle>() {};
    private static final TypeMarker<HCircleObstacle> circleType = new TypeMarker<HCircleObstacle>() {};

    public static enum ObstacleType {
        POLYGON,
        CIRCLE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final DoubleProperty safetyDistance = new SimpleDoubleProperty(this, "safetyDistance", 0.0);

    protected HObstacle() {
    }

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

    public boolean isPolygon() {
        return false;
    }

    public boolean isCircle() {
        return false;
    }
    
    @DeserializedJSONDeterminer
    public static final TypeMarker<? extends HObstacle> abstractDeterminer(JSONObject jsonEntry) throws JSONDeserializerException {
        if (!jsonEntry.containsKey("obstacle_type")) {
            throw new JSONDeserializerException("Unable to determine which type of obstacle to deserialize to since the key \"obstacle_type\" is missing in the JSONObject.");
        }
        String typeString = ((JSONString) jsonEntry.get("obstacle_type")).getString();
        switch (typeString) {
            case "polygon":
                return polygonType;
            case "circle":
                return circleType;
            default:
                throw new JSONDeserializerException("Unrecognized obstacle type string.");
        }
    }
}
