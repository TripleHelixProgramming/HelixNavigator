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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Justin Babilino
 */
public class HCircleObstacle extends HAbstractObstacle {

    private final DoubleProperty centerX = new SimpleDoubleProperty(this, "centerX", 0.0);
    private final DoubleProperty centerY = new SimpleDoubleProperty(this, "centerY", 0.0);
    private final DoubleProperty radius = new SimpleDoubleProperty(this, "radius", 1.0);

    public HCircleObstacle() {
    }

    @Override
    public void translateRelativeX(double x) {
        setCenterX(getCenterX() + x);
    }

    @Override
    public void translateRelativeY(double y) {
        setCenterY(getCenterY() + y);
    }

    public final DoubleProperty centerXProperty() {
        return centerX;
    }

    public final void setCenterX(double value) {
        centerX.set(value);
    }

    @SerializedJSONObjectValue(key = "center_x")
    public final double getCenterX() {
        return centerX.get();
    }

    public final DoubleProperty centerYProperty() {
        return centerY;
    }

    public final void setCenterY(double value) {
        centerY.set(value);
    }

    @SerializedJSONObjectValue(key = "center_y")
    public final double getCenterY() {
        return centerY.get();
    }

    public final DoubleProperty radiusProperty() {
        return radius;
    }

    public final void setRadius(double value) {
        radius.set(value);
    }

    @SerializedJSONObjectValue(key = "radius")
    public final double getRadius() {
        return radius.get();
    }

    public ObstacleType getObstacleType() {
        return ObstacleType.CIRCLE;
    }
}
