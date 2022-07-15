package org.team2363.helixnavigator.document.obstacle;

import org.team2363.helixnavigator.document.HPathElement;
import org.team2363.helixnavigator.global.Standards.DefaultRobotConfiguration;
import org.team2363.helixtrajectory.Obstacle;
import org.team2363.helixtrajectory.ObstaclePoint;

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

public abstract class HObstacle extends HPathElement {

    private static final TypeMarker<HCircleObstacle> CIRCLE_TYPE = new TypeMarker<HCircleObstacle>() {};
    private static final TypeMarker<HPolygonObstacle> POLYGON_TYPE = new TypeMarker<HPolygonObstacle>() {};
    private static final TypeMarker<HRectangleObstacle> RECTANGLE_TYPE = new TypeMarker<HRectangleObstacle>() {};

    public static enum ObstacleType {
        CIRCLE,
        POLYGON,
        RECTANGLE;

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
    public static final TypeMarker<? extends HObstacle> abstractDeterminer(JSONObject jsonEntry) throws JSONDeserializerException {
        if (!jsonEntry.containsKey("obstacle_type")) {
            throw new JSONDeserializerException("Unable to determine which type of obstacle to deserialize to since the key \"obstacle_type\" is missing in the JSONObject.");
        }
        String typeString = ((JSONString) jsonEntry.get("obstacle_type")).getString();
        switch (typeString.trim().toLowerCase()) {
            case "circle":
                return CIRCLE_TYPE;
            case "polygon":
                return POLYGON_TYPE;
            case "rectangle":
                return RECTANGLE_TYPE;
            default:
                throw new JSONDeserializerException("Unrecognized obstacle type string: \"" + typeString + "\"");
        }
    }

    public static Obstacle defaultBumpers() {
        ObstaclePoint point1 = new ObstaclePoint(+DefaultRobotConfiguration.BUMPER_LENGTH / 2, +DefaultRobotConfiguration.BUMPER_WIDTH / 2);
        ObstaclePoint point2 = new ObstaclePoint(-DefaultRobotConfiguration.BUMPER_LENGTH / 2, +DefaultRobotConfiguration.BUMPER_WIDTH / 2);
        ObstaclePoint point3 = new ObstaclePoint(-DefaultRobotConfiguration.BUMPER_LENGTH / 2, -DefaultRobotConfiguration.BUMPER_WIDTH / 2);
        ObstaclePoint point4 = new ObstaclePoint(+DefaultRobotConfiguration.BUMPER_LENGTH / 2, -DefaultRobotConfiguration.BUMPER_WIDTH / 2);
        return new Obstacle(0, point1, point2, point3, point4);
    }
}
