package org.team2363.helixnavigator.document.obstacle;

import org.team2363.helixnavigator.document.HPathElement;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HPolygonPoint extends HPathElement {

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);

    @DeserializedJSONConstructor
    public HPolygonPoint() {
    }

    @Override
    public void translateRelativeX(double dx) {
        setX(getX() + dx);
    }

    @Override
    public void translateRelativeY(double dy) {
        setY(getY() + dy);
    }

    public final DoubleProperty xProperty() {
        return x;
    }

    @DeserializedJSONTarget
    public final void setX(@DeserializedJSONObjectValue(key = "x") double value) {
        x.set(value);
    }

    @SerializedJSONObjectValue(key = "x")
    public final double getX() {
        return x.get();
    }

    public final DoubleProperty yProperty() {
        return y;
    }

    @DeserializedJSONTarget
    public final void setY(@DeserializedJSONObjectValue(key = "y") double value) {
        y.set(value);
    }

    @SerializedJSONObjectValue(key = "y")
    public final double getY() {
        return y.get();
    }

    @Override
    public String toString() {
        return getName();
    }
}