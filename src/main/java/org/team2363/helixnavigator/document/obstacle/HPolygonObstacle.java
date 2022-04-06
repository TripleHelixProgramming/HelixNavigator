package org.team2363.helixnavigator.document.obstacle;

import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HPolygonObstacle extends HObstacle {

    private final ObservableList<HPolygonPoint> points = FXCollections.<HPolygonPoint>observableArrayList();

    public HPolygonObstacle() {
        HPolygonPoint[] initialPoints = new HPolygonPoint[3];
        for (int i = 0; i < initialPoints.length; i++) {
            initialPoints[i] = new HPolygonPoint();
        }
        initialPoints[0].setX(0.0);
        initialPoints[0].setY(0.0);
        initialPoints[1].setX(80.0);
        initialPoints[1].setY(0.0);
        initialPoints[2].setX(40.0);
        initialPoints[2].setY(80.0);
        points.addAll(initialPoints[0], initialPoints[1], initialPoints[2]);
    }

    @DeserializedJSONConstructor
    public HPolygonObstacle(@DeserializedJSONObjectValue(key = "points") List<? extends HPolygonPoint> initialPoints) {
        points.setAll(initialPoints);
    }

    @Override
    public void translateRelativeX(double dx) {
        points.forEach(point -> point.translateRelativeX(dx));
    }

    @Override
    public void translateRelativeY(double dy) {
        points.forEach(point -> point.translateRelativeY(dy));
    }

    @SerializedJSONObjectValue(key = "points")
    public ObservableList<HPolygonPoint> getPoints() {
        return points;
    }

    public ObstacleType getObstacleType() {
        return ObstacleType.POLYGON;
    }

    @Override
    public boolean isPolygon() {
        return true;
    }
}