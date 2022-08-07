package org.team2363.helixnavigator.document.compiled;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HolonomicPositionState extends VelocityState {

    private final DoubleProperty velocityX = new SimpleDoubleProperty(this, "velocityX", 0.0);
    private final DoubleProperty velocityY = new SimpleDoubleProperty(this, "velocityX", 0.0);
    private final DoubleProperty angularVelocity = new SimpleDoubleProperty(this, "angularVelocity", 0.0);
}