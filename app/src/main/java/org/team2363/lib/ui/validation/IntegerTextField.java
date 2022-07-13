package org.team2363.lib.ui.validation;

import static org.team2363.lib.ui.validation.IntegerValidator.INTEGER_CONVERTER;
import static org.team2363.lib.ui.validation.IntegerValidator.INTEGER_FILTER;

import java.util.function.UnaryOperator;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class IntegerTextField extends TextField {

    private final ObjectProperty<Integer> value;

    private IntegerTextField(StringConverter<Integer> converter) {
        TextFormatter<Integer> formatter = new TextFormatter<Integer>(converter, 0, INTEGER_FILTER);
        value = formatter.valueProperty();
        setTextFormatter(formatter);
        setText("0");
        focusedProperty().addListener((val, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater(this::selectAll);
            }
        });
    }

    public IntegerTextField() {
        this(INTEGER_CONVERTER);
    }

    public IntegerTextField(int lowerBound, int upperBound) {
        this(IntegerValidator.converter(lowerBound, upperBound));
    }

    public IntegerTextField(UnaryOperator<Integer> transformation) {
        this(IntegerValidator.converter(transformation));
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