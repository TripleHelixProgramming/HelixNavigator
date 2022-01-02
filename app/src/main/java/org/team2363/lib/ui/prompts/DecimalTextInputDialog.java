package org.team2363.lib.ui.prompts;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;

public class DecimalTextInputDialog extends TextInputDialog {

    private final ObjectProperty<Double> value;

    public DecimalTextInputDialog() {
        TextFormatter<Double> formatter = new TextFormatter<>(DecimalTextField.DECIMAL_CONVERTER, 0.0, DecimalTextField.DECIMAL_FILTER);
        value = formatter.valueProperty();
        getEditor().setTextFormatter(formatter);
        getEditor().setText("0.0");
        getEditor().focusedProperty().addListener((val, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater(getEditor()::selectAll);
            }
        });
        setHeaderText("Enter a number");
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