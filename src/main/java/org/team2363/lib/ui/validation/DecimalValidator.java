package org.team2363.lib.ui.validation;

import static org.team2363.lib.ui.validation.FilteredTextField.filterFor;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class DecimalValidator {

    public static final Pattern DECIMAL_PATTERN = Pattern.compile("-?\\d*(\\.\\d*)?((e|E)\\d*)?");
    public static final UnaryOperator<TextFormatter.Change> DECIMAL_FILTER = filterFor(Integer.MAX_VALUE, DECIMAL_PATTERN);
    public static final StringConverter<Double> DECIMAL_CONVERTER = new DecimalValidator().getConverter();

    public static StringConverter<Double> converter(double lowerBound, double upperBound) {
        return new DecimalValidator() {

            @Override
            protected double transformInput(double input) {
                return Math.min(Math.max(input, lowerBound), upperBound);
            }
        }.getConverter();
    }

    public static StringConverter<Double> converter(UnaryOperator<Double> transformation) {
        return new DecimalValidator() {

            @Override
            protected double transformInput(double input) {
                return transformation.apply(input);
            }
        }.getConverter();
    }

    private final StringConverter<Double> converter = new StringConverter<>() {

        @Override
        public String toString(Double object) {
            if (object != null) {
                return Double.toString(object);
            } else {
                return "";
            }
        }

        @Override
        public Double fromString(String string) {
            double rawValue;
            try {
                rawValue = Double.parseDouble(string);
            } catch (NumberFormatException e) {
                rawValue = 0.0;
            }
            return transformInput(rawValue);
        }
    };

    private DecimalValidator() {
    }

    protected double transformInput(double input) {
        return input;
    }

    public StringConverter<Double> getConverter() {
        return converter;
    }
}