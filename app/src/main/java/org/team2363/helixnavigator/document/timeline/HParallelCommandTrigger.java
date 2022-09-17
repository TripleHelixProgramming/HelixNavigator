package org.team2363.helixnavigator.document.timeline;

import com.jlbabilino.json.DeserializedJSONConstructor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Transform;

public class HParallelCommandTrigger extends HAction {

    private final DoubleProperty timeOffset = new SimpleDoubleProperty(this, "timeOffset", 0.0);
    private final DoubleProperty negativeCommandDuration = new SimpleDoubleProperty(this, "negativeCommandDuration", 0.0);
    private final DoubleProperty positiveCommandDuration = new SimpleDoubleProperty(this, "positiveCommandDuration", 1.0);
    private final BooleanProperty stopAtDuration = new SimpleBooleanProperty(this, "stopAtDuration", true);

    @DeserializedJSONConstructor
    public HParallelCommandTrigger() {
    }

    @Override
    public TimelineElementType getTimelineElementType() {
        return TimelineElementType.PARALLEL_COMMAND_TRIGGER;
    }

    @Override
    public void transformRelative(Transform transform) {
    }
    @Override
    public void translateRelativeX(double x) {
    }
    @Override
    public void translateRelativeY(double y) {
    }
    
    public final DoubleProperty timeOffsetProperty() {
        return timeOffset;
    }
    public final void setTimeOffset(double value) {
        timeOffset.set(value);
    }
    public final double getTimeOffset() {
        return timeOffset.get();
    }

    public final DoubleProperty negativeCommandDurationProperty() {
        return negativeCommandDuration;
    }
    public final void setNegativeCommandDuration(double value) {
        negativeCommandDuration.set(value);
    }
    public final double getNegativeCommandDuration() {
        return negativeCommandDuration.get();
    }

    public final DoubleProperty positiveCommandDurationProperty() {
        return positiveCommandDuration;
    }
    public final void setPositiveCommandDuration(double value) {
        positiveCommandDuration.set(value);
    }
    public final double getPositiveCommandDuration() {
        return positiveCommandDuration.get();
    }

    public final BooleanProperty stopAtDurationProperty() {
        return stopAtDuration;
    }
    public final void setStopAtDuration(boolean value) {
        stopAtDuration.set(value);
    }
    public final boolean getStopAtDuration() {
        return stopAtDuration.get();
    }
}