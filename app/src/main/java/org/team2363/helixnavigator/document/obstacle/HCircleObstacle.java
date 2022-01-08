package org.team2363.helixnavigator.document.obstacle;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HCircleObstacle extends HObstacle {

    private final DoubleProperty centerX = new SimpleDoubleProperty(this, "centerX", 0.0);
    private final DoubleProperty centerY = new SimpleDoubleProperty(this, "centerY", 0.0);
    private final DoubleProperty radius = new SimpleDoubleProperty(this, "radius", 1.0);

    @DeserializedJSONConstructor
    public HCircleObstacle() {
    }

    @Override
    public void translateRelativeX(double dx) {
        setCenterX(getCenterX() + dx);
    }

    @Override
    public void translateRelativeY(double dy) {
        setCenterY(getCenterY() + dy);
    }

    public final DoubleProperty centerXProperty() {
        return centerX;
    }

    @DeserializedJSONTarget
    public final void setCenterX(@DeserializedJSONObjectValue(key = "center_x") double value) {
        centerX.set(value);
    }

    @SerializedJSONObjectValue(key = "center_x")
    public final double getCenterX() {
        return centerX.get();
    }

    public final DoubleProperty centerYProperty() {
        return centerY;
    }

    @DeserializedJSONTarget
    public final void setCenterY(@DeserializedJSONObjectValue(key = "center_y") double value) {
        centerY.set(value);
    }

    @SerializedJSONObjectValue(key = "center_y")
    public final double getCenterY() {
        return centerY.get();
    }

    public final DoubleProperty radiusProperty() {
        return radius;
    }

    @DeserializedJSONTarget
    public final void setRadius(@DeserializedJSONObjectValue(key = "radius") double value) {
        radius.set(value);
    }

    @SerializedJSONObjectValue(key = "radius")
    public final double getRadius() {
        return radius.get();
    }

    @Override
    public ObstacleType getObstacleType() {
        return ObstacleType.CIRCLE;
    }

    @Override
    public boolean isCircle() {
        return true;
    }
}
