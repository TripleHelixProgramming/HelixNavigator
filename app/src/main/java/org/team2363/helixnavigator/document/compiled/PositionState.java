package org.team2363.helixnavigator.document.compiled;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;

public class PositionState {
    
    private final ReadOnlyDoubleWrapper x = new ReadOnlyDoubleWrapper(this, "x", 0.0);
    private final ReadOnlyDoubleWrapper y = new ReadOnlyDoubleWrapper(this, "y", 0.0);
    private final ReadOnlyDoubleWrapper heading = new ReadOnlyDoubleWrapper(this, "heading", 0.0);

    public PositionState() {
    }

    void applyTrajectorySample(HTrajectorySample sample) {
        setX(sample.x);
        setY(sample.y);
        setHeading(sample.heading);
    }

    public final ReadOnlyDoubleProperty xProperty() {
        return x.getReadOnlyProperty();
    }
    final void setX(double value) {
        x.set(value);
    }
    public final double getX() {
        return x.get();
    }

    public final ReadOnlyDoubleProperty yProperty() {
        return y.getReadOnlyProperty();
    }
    final void setY(double value) {
        y.set(value);
    }
    public final double getY() {
        return y.get();
    }

    public final ReadOnlyDoubleProperty headingProperty() {
        return heading.getReadOnlyProperty();
    }
    final void setHeading(double value) {
        heading.set(value);
    }
    public final double getHeading() {
        return heading.get();
    }
}