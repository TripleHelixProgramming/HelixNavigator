package org.team2363.lib.ui.validation;

import static org.team2363.lib.ui.validation.FilteredTextField.filterFor;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class IntegerValidator {

    public static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d*((e|E)\\d*)?");
    public static final UnaryOperator<TextFormatter.Change> INTEGER_FILTER = filterFor(Integer.MAX_VALUE, INTEGER_PATTERN);
    public static final StringConverter<Integer> INTEGER_CONVERTER = new IntegerValidator().getConverter();

    public static StringConverter<Integer> converter(int lowerBound, int upperBound) {
        return new IntegerValidator() {

            @Override
            protected int transformInput(int input) {
                return Math.min(Math.max(input, lowerBound), upperBound);
            }
        }.getConverter();
    }

    public static StringConverter<Integer> converter(UnaryOperator<Integer> transformation) {
        return new IntegerValidator() {

            @Override
            protected int transformInput(int input) {
                return transformation.apply(input);
            }
        }.getConverter();
    }

    private final StringConverter<Integer> converter = new StringConverter<>() {

        @Override
        public String toString(Integer object) {
            if (object != null) {
                return Integer.toString(object);
            } else {
                return "";
            }
        }

        @Override
        public Integer fromString(String string) {
            int rawValue;
            try {
                rawValue = Integer.parseInt(string);
            } catch (NumberFormatException e) {
                rawValue = 0;
            }
            return transformInput(rawValue);
        }
    };

    private IntegerValidator() {
    }

    protected int transformInput(int input) {
        return input;
    }

    public StringConverter<Integer> getConverter() {
        return converter;
    }
}