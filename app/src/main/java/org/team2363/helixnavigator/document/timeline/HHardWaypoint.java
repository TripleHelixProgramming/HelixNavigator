package org.team2363.helixnavigator.document.timeline;

import java.util.List;

import org.team2363.helixtrajectory.HolonomicWaypoint;
import org.team2363.helixtrajectory.InitialGuessPoint;
import org.team2363.helixtrajectory.Obstacle;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Transform;

public class HHardWaypoint extends HWaypoint {
    
    private final DoubleProperty heading = new SimpleDoubleProperty(this, "heading", 0.0);

    @DeserializedJSONConstructor
    public HHardWaypoint() {
    }

    @Override
    public void transformRelative(Transform transform) {
        super.transformRelative(transform);
        double deltaAngle = Math.atan2(transform.getMyx(), transform.getMxx());
        setHeading(getHeading() + deltaAngle);
    }

    @Override
    public WaypointType getWaypointType() {
        return WaypointType.HARD;
    }

    @Override
    public boolean isHard() {
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

    public HolonomicWaypoint toWaypoint(List<InitialGuessPoint> initialGuessPoints, List<Obstacle> obstacles) {
        return new HolonomicWaypoint(getX(), getY(), getHeading(), 0.0, 0.0, 0.0, true, true, true, false, false, false, false, 100, initialGuessPoints, obstacles);
    }
}