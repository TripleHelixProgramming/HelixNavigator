package org.team2363.helixnavigator.document.obstacle;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HRectangleObstacle extends HObstacle {

    private final DoubleProperty centerX = new SimpleDoubleProperty(this, "centerX", 0.0);
    private final DoubleProperty centerY = new SimpleDoubleProperty(this, "centerY", 0.0);
    private final DoubleProperty length = new SimpleDoubleProperty(this, "length", 20.0);
    private final DoubleProperty width = new SimpleDoubleProperty(this, "width", 10.0);
    private final DoubleProperty rotateAngle = new SimpleDoubleProperty(this, "rotateAngle", 0.0);

    @DeserializedJSONConstructor
    public HRectangleObstacle() {
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

    public final DoubleProperty lengthProperty() {
        return length;
    }

    @DeserializedJSONTarget
    public final void setLength(@DeserializedJSONObjectValue(key = "length") double value) {
        length.set(value);
    }

    @SerializedJSONObjectValue(key = "length")
    public final double getLength() {
        return length.get();
    }

    public final DoubleProperty widthProperty() {
        return width;
    }

    @DeserializedJSONTarget
    public final void setWidth(@DeserializedJSONObjectValue(key = "width") double value) {
        width.set(value);
    }

    @SerializedJSONObjectValue(key = "length")
    public final double getWidth() {
        return width.get();
    }

    public final DoubleProperty rotateAngleProperty() {
        return rotateAngle;
    }

    @DeserializedJSONTarget
    public final void setRotateAngle(@DeserializedJSONObjectValue(key = "rotate_angle") double value) {
        rotateAngle.set(value);
    }

    @SerializedJSONObjectValue(key = "rotate_angle")
    public final double getRotateAngle() {
        return rotateAngle.get();
    }

    @Override
    public ObstacleType getObstacleType() {
        return ObstacleType.RECTANGLE;
    }

    @Override
    public boolean isRectangle() {
        return true;
    }
}
