package org.team2363.lib.ui.prompts;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class FilteredTextField extends TextField {

    private final IntegerProperty maxChars = new SimpleIntegerProperty(this, "maxChars", Integer.MAX_VALUE);
    private final ObjectProperty<Pattern> validator = new SimpleObjectProperty<>(this, "validator", Pattern.compile(".*"));

    protected final UnaryOperator<TextFormatter.Change> filter;

    public FilteredTextField() {
        filter = input -> {
            String newText = input.getControlNewText();
            Matcher matcher = getValidator().matcher(newText);
            if (!matcher.matches() || newText.length() > getMaxChars()) {
                input.setText("");
            }
            return input;
        };
        setTextFormatter(new TextFormatter<TextFormatter.Change>(filter));
        maxChars.addListener((currentVal, oldVal, newVal) -> {
            if (getText().length() > newVal.intValue()) {
                setText(getText().substring(0, newVal.intValue()));
            }
        });
    }

    public FilteredTextField(int maxChars, Pattern validator) {
        this();
        setMaxChars(maxChars);
        setValidator(validator);
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