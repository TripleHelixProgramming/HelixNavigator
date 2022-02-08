package org.team2363.lib.ui.prompts;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ConstrainedDecimalTextField extends DecimalTextField {

    private final DoubleProperty minValue = new SimpleDoubleProperty(this, "minValue", Double.MIN_VALUE);
    private final DoubleProperty maxValue = new SimpleDoubleProperty(this, "maxValue", Double.MAX_VALUE);

    public ConstrainedDecimalTextField() {
    }

    public ConstrainedDecimalTextField(double minValue, double maxValue) {
        setMinValue(minValue);
        setMaxValue(maxValue);
    }

    @Override
    protected double transformInput(double input) {
        if (input < getMinValue()) {
            input = getMinValue();
        } else if (input > getMaxValue()) {
            input = getMaxValue();
        }
        return input;
    }

    public final DoubleProperty minValueProperty() {
        return minValue;
    }

    public final void setMinValue(double value) {
        minValue.set(value);
    }

    public final double getMinValue() {
        return minValue.get();
    }

    public final DoubleProperty maxValueProperty() {
        return maxValue;
    }

    public final void setMaxValue(double value) {
        maxValue.set(value);
    }

    public final double getMaxValue() {
        return maxValue.get();
    }
}
