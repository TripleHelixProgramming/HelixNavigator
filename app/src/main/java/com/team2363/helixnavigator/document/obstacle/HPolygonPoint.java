package com.team2363.helixnavigator.document.obstacle;

import com.team2363.helixnavigator.document.HPathElement;
import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HPolygonPoint extends HPathElement {
    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);

    public HPolygonPoint() {
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

    public String toString() {
        return "x: " + getX() + " y: " + getY();
    }
}