package org.team2363.helixnavigator.document.timeline.waypoint;

import org.team2363.helixnavigator.document.timeline.HTimelineElement;
import org.team2363.helixnavigator.document.timeline.HTimelineElement.TimelineElementType;
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
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);
    
    @DeserializedJSONConstructor
    public HInitialGuessPoint() {
    }

    @Override
    public void transformRelative(Transform transform) {
        Point2D newPoint = transform.transform(getX(), getY());
        setX(newPoint.getX());
        setY(newPoint.getY());
        double deltaAngle = Math.atan2(transform.getMyx(), transform.getMxx());
        setHeading(getHeading() + deltaAngle);
    }
    @Override
    public void translateRelativeX(double x) {
        setX(getX() + x);
    }
    @Override
    public void translateRelativeY(double y) {
        setY(getY() + y);
    }

    @Override
    public TimelineElementType getTimelineElementType() {
        return TimelineElementType.INITIAL_GUESS_POINT;
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

    public final DoubleProperty headingProperty() {
        return heading;
    }
    @DeserializedJSONTarget
    public final void setHeading(@DeserializedJSONObjectValue(key = "heading") double value) {
        heading.set(value);
    }
    @SerializedJSONObjectValue(key = "heading")
    public final double getHeading() {
        return heading.get();
    }

    public InitialGuessPoint toInitialGuessPoint() {
        return new InitialGuessPoint(getX(), getY(), getHeading());
    }
}