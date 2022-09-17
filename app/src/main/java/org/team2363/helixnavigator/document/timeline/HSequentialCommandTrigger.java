package org.team2363.helixnavigator.document.timeline;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.transform.Transform;

public class HSequentialCommandTrigger extends HSequentialAction {

    private final ReadOnlyDoubleWrapper commandDuration = new ReadOnlyDoubleWrapper(this, "commandDuration", 0.0);
    private final BooleanProperty stopAtDuration = new SimpleBooleanProperty(this, "stopAtDuration", true);

    public HSequentialCommandTrigger() {
    }

    @Override
    public TimelineElementType getTimelineElementType() {
        return TimelineElementType.SEQUENTIAL_COMMAND_TRIGGER;
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
    
    @Override
    public ReadOnlyDoubleProperty durationProperty() {
        return commandDuration.getReadOnlyProperty(); // it's ok that 
    }

    public final DoubleProperty commandDurationProperty() {
        return commandDuration;
    }
    public final void setCommandDuration(double value) {
        commandDuration.set(value);
    }
    public double getCommandDuration() {
        return commandDuration.get();
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