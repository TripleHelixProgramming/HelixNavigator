package org.team2363.helixnavigator.document.timeline;

import org.team2363.helixtrajectory.InitialGuessPoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Transform;

public class HInitialGuessWaypoint extends HWaypoint {

    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);
    
    @DeserializedJSONConstructor
    public HInitialGuessWaypoint() {
    }

    @Override
    public void transformRelative(Transform transform) {
        super.transformRelative(transform);
        double deltaAngle = Math.atan2(transform.getMyx(), transform.getMxx());
        setHeading(getHeading() + deltaAngle);
    }

    @Override
    public WaypointType getWaypointType() {
        return WaypointType.INITIAL_GUESS;
    }

    @Override
    public boolean isInitialGuess() {
        return true;
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