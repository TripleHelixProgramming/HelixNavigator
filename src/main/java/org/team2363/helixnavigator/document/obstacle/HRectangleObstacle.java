package org.team2363.helixnavigator.document.obstacle;

import org.team2363.helixtrajectory.Obstacle;
import org.team2363.helixtrajectory.ObstaclePoint;

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
    public Obstacle toObstacle() {
        double diagonal = Math.hypot(getLength() / 2, getWidth() / 2);
        double angle = Math.atan(getWidth() / getLength());
        double[] angles = {+angle, +(Math.PI - angle), -(Math.PI - angle), -angle};
        double[] x = {getCenterX() + diagonal * Math.cos(getRotateAngle() + angles[0]),
                      getCenterX() + diagonal * Math.cos(getRotateAngle() + angles[1]),
                      getCenterX() + diagonal * Math.cos(getRotateAngle() + angles[2]),
                      getCenterX() + diagonal * Math.cos(getRotateAngle() + angles[3])};
        double[] y = {getCenterY() + diagonal * Math.sin(getRotateAngle() + angles[0]),
                      getCenterY() + diagonal * Math.sin(getRotateAngle() + angles[1]),
                      getCenterY() + diagonal * Math.sin(getRotateAngle() + angles[2]),
                      getCenterY() + diagonal * Math.sin(getRotateAngle() + angles[3])};
        ObstaclePoint[] obstaclePoints = {new ObstaclePoint(x[0], y[0]),
                                          new ObstaclePoint(x[1], y[1]),
                                          new ObstaclePoint(x[2], y[2]),
                                          new ObstaclePoint(x[3], y[3])};
        return new Obstacle(getSafetyDistance(), obstaclePoints);
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

    @SerializedJSONObjectValue(key = "width")
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
