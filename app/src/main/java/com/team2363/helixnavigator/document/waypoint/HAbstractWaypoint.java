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
package com.team2363.helixnavigator.document.waypoint;

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
public abstract class HAbstractWaypoint extends HBasePathElement {
    
    public static enum WaypointType {
        SOFT, HARD
    }

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);

    private final StringProperty name = new SimpleStringProperty(this, "name", "");

    protected HAbstractWaypoint() {
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

    public final void setX(double value) {
        x.set(value);
    }

    @SerializedJSONObjectValue(key = "x")
    public final double getX() {
        return x.get();
    }

    public final DoubleProperty yProperty() {
        return y;
    }

    public final void setY(double value) {
        y.set(value);
    }

    @SerializedJSONObjectValue(key = "y")
    public final double getY() {
        return y.get();
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
    
    @SerializedJSONObjectValue(key = "waypoint_type")
    public abstract WaypointType getWaypointType();
}