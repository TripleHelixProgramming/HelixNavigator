package org.team2363.lib.ui.validation;

import static org.team2363.lib.ui.validation.FilteredTextField.filterFor;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;

public class FilteredTextInputDialog extends TextInputDialog {

    private IntegerProperty maxChars = new SimpleIntegerProperty(this, "maxChars", Integer.MAX_VALUE);
    private ObjectProperty<Pattern> validator = new SimpleObjectProperty<>(this, "validator", Pattern.compile(".*"));

    public FilteredTextInputDialog() {
        UnaryOperator<TextFormatter.Change> filter = filterFor(maxChars, validator);
        getEditor().setTextFormatter(new TextFormatter<TextFormatter.Change>(filter));
        maxChars.addListener((currentVal, oldVal, newVal) -> {
            if (getEditor().getText().length() > newVal.intValue()) {
                getEditor().setText(getEditor().getText().substring(0, newVal.intValue()));
            }
        });
    }

    public final IntegerProperty maxCharsProperty() {
        return maxChars;
    }

    public final void setMaxChars(int value) {
        maxChars.set(value);
    }

    public final int getMaxChars() {
        return maxChars.get();
    }

    public final ObjectProperty<Pattern> validatorProperty() {
        return validator;
    }

    public final void setValidator(Pattern value) {
        validator.set(value);
    }

    public final Pattern getValidator() {
        return validator.get();
    }
}