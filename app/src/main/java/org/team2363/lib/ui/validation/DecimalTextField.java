package org.team2363.lib.ui.validation;

import static org.team2363.lib.ui.validation.DecimalValidator.DECIMAL_CONVERTER;
import static org.team2363.lib.ui.validation.DecimalValidator.DECIMAL_FILTER;

import java.util.function.UnaryOperator;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class DecimalTextField extends TextField {

    private final ObjectProperty<Double> value;

    private DecimalTextField(StringConverter<Double> converter) {
        TextFormatter<Double> formatter = new TextFormatter<Double>(converter, 0.0, DECIMAL_FILTER);
        value = formatter.valueProperty();
        setTextFormatter(formatter);
        setText("0.0");
        focusedProperty().addListener((val, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater(this::selectAll);
            }
        });
        setOnAction(event -> {
            selectAll();
        });
    }

    public DecimalTextField() {
        this(DECIMAL_CONVERTER);
    }

    public DecimalTextField(double lowerBound, double upperBound) {
        this(DecimalValidator.converter(lowerBound, upperBound));
    }

    public DecimalTextField(UnaryOperator<Double> transformation) {
        this(DecimalValidator.converter(transformation));
    }

    public final ObjectProperty<Double> valueProperty() {
        return value;
    }

    public final void setValue(double value) {
        this.value.set(value);
    }
    
    public final double getValue() {
        return value.get();
    }
}