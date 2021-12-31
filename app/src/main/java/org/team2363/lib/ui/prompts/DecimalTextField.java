package org.team2363.lib.ui.prompts;

import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class DecimalTextField extends FilteredTextField {

    private static final StringConverter<Double> DECIMAL_CONVERTER = new StringConverter<Double>() {

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
            if (string.length() == 0 || string.equals("-")) {
                return 0.0;
            } else {
                return Double.parseDouble(string);
            }
        }
        
    };

    private final ObjectProperty<Double> value;

    public DecimalTextField() {
        super(Integer.MAX_VALUE, Pattern.compile("-?\\d*(\\.\\d*)?"));
        TextFormatter<Double> formatter = new TextFormatter<Double>(DECIMAL_CONVERTER, 0.0, filter);
        value = formatter.valueProperty();
        setTextFormatter(formatter);
        setText("0.0");
        focusedProperty().addListener((val, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater(this::selectAll);
            }
        });
    }

    public final ObjectProperty<Double> valueProperty() {
        return value;
    }
    
    public final double getValue() {
        return value.get();
    }
}