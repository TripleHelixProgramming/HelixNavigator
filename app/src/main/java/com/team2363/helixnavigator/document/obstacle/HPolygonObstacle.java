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
package com.team2363.helixnavigator.document.obstacle;

import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Justin Babilino
 */
public class HPolygonObstacle extends HAbstractObstacle {
    private final ObservableList<HPolygonPoint> points = FXCollections.<HPolygonPoint>observableArrayList();

    public HPolygonObstacle() {
        HPolygonPoint initialPoint0, initialPoint1, initialPoint2;
        initialPoint0 = new HPolygonPoint();
        initialPoint1 = new HPolygonPoint();
        initialPoint2 = new HPolygonPoint();
        initialPoint0.setX(0.0);
        initialPoint0.setY(0.0);
        initialPoint1.setX(2.0);
        initialPoint1.setY(0.0);
        initialPoint2.setX(2.0);
        initialPoint2.setY(2.0);
        points.addAll(initialPoint0, initialPoint1, initialPoint2);
    }

    @Override
    public void translateRelativeX(double x) {
        points.forEach(point -> point.translateRelativeX(x));
    }

    @Override
    public void translateRelativeY(double y) {
        points.forEach(point -> point.translateRelativeY(y));
    }

    @SerializedJSONObjectValue(key = "points")
    public ObservableList<HPolygonPoint> getPoints() {
        return points;
    }

    public ObstacleType getObstacleType() {
        return ObstacleType.POLYGON;
    }
}
