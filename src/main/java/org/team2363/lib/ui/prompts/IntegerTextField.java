package org.team2363.lib.ui.prompts;

import static org.team2363.lib.ui.prompts.FilteredTextField.filterFor;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class IntegerTextField extends TextField {

    public static final StringConverter<Integer> INTEGER_CONVERTER = new StringConverter<Integer>() {

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
            if (string.length() == 0 || string.equals("-")) {
                return 0;
            } else {
                return Integer.parseInt(string);
            }
        }
    };
    public static final Pattern INTEGER_VALIDATOR = Pattern.compile("-?\\d*");
    public static final UnaryOperator<TextFormatter.Change> INTEGER_FILTER = filterFor(Integer.MAX_VALUE, INTEGER_VALIDATOR);

    private final ObjectProperty<Integer> value;

    public IntegerTextField() {
        TextFormatter<Integer> formatter = new TextFormatter<Integer>(INTEGER_CONVERTER, 0, INTEGER_FILTER);
        value = formatter.valueProperty();
        setTextFormatter(formatter);
        setText("0");
        focusedProperty().addListener((val, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater(this::selectAll);
            }
        });
    }

    public final ObjectProperty<Integer> valueProperty() {
        return value;
    }

    public final void setValue(int value) {
        this.value.set(value);
    }
    
    public final int getValue() {
        return value.get();
    }
}