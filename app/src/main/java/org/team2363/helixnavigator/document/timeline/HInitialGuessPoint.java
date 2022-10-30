package org.team2363.helixnavigator.document.timeline;

import org.team2363.helixtrajectory.InitialGuessPoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.transform.Transform;

public class HInitialGuessPoint extends HTimelineElement {

    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);
    
    @DeserializedJSONConstructor
    public HInitialGuessPoint() {
    }

    @Override
    public void transformRelative(Transform transform) {
        Point2D newPoint = transform.transform(getX(), getY());
        setX(newPoint.getX());
        setY(newPoint.getY());
    }
    @Override
    public void translateRelativeX(double dx) {
        setX(getX() + dx);
    }
    @Override
    public void translateRelativeY(double dy) {
        setY(getY() + dy);
    }


    @Override
    public TimelineElementType getTimelineElementType() {
        return TimelineElementType.INITIAL_GUESS;
    }
    @Override
    public boolean isInitialGuessPoint() {
        return true;
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

    public InitialGuessPoint toInitialGuessPoint() {
        return new InitialGuessPoint(getX(), getY(), 0.0);
    }
}