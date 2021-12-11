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

import com.team2363.helixnavigator.document.HBasePathElement;
import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Justin Babilino
 */
public abstract class HAbstractObstacle extends HBasePathElement {

    public static enum ObstacleType {
        POLYGON, CIRCLE
    }

    private final DoubleProperty safetyDistance = new SimpleDoubleProperty(this, "safetyDistance", 0.0);

    private final StringProperty name = new SimpleStringProperty(this, "name", "");

    protected HAbstractObstacle() {
    }

    public final DoubleProperty safetyDistanceProperty() {
        return safetyDistance;
    }

    public final void setSafetyDistance(double value) {
        safetyDistance.set(value);
    }

    @SerializedJSONObjectValue(key = "safety_distance")
    public final double getSafetyDistance() {
        return safetyDistance.get();
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final void setName(String value) {
        name.set(value);
    }

    @SerializedJSONObjectValue(key = "name")
    public final String getName() {
        return name.get();
    }

    @SerializedJSONObjectValue(key = "obstacle_type")
    public abstract ObstacleType getObstacleType();
}
