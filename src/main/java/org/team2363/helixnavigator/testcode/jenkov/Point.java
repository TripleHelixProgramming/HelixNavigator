package org.team2363.helixnavigator.testcode.jenkov;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Point {

    private DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);

    public Point() {
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double value) {
        x.set(value);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double value) {
        y.set(value);
    }

    public double getY() {
        return y.get();
    }
}