/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.team2363.helixnavigator.document.obstacle;

import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HPolygonObstacle extends HObstacle {

    private final ObservableList<HPolygonPoint> points = FXCollections.<HPolygonPoint>observableArrayList();

    @DeserializedJSONConstructor
    public HPolygonObstacle() {
        HPolygonPoint[] initialPoints = new HPolygonPoint[3];
        for (int i = 0; i < initialPoints.length; i++) {
            initialPoints[i] = new HPolygonPoint();
        }
        initialPoints[0].setX(0.0);
        initialPoints[0].setY(0.0);
        initialPoints[1].setX(2.0);
        initialPoints[1].setY(0.0);
        initialPoints[2].setX(1.0);
        initialPoints[2].setY(2.0);
        points.addAll(initialPoints[0], initialPoints[1], initialPoints[2]);
    }

    @Override
    public void translateRelativeX(double x) {
        points.forEach(point -> point.translateRelativeX(x));
    }

    @Override
    public void translateRelativeY(double y) {
        points.forEach(point -> point.translateRelativeY(y));
    }

    @DeserializedJSONTarget
    public final void setPoints(@DeserializedJSONObjectValue(key = "points") List<? extends HPolygonPoint> newPoints) {
        points.setAll(newPoints);
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