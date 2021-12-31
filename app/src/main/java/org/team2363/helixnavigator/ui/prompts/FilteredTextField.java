package org.team2363.helixnavigator.ui.prompts;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class FilteredTextField extends TextField {

    public FilteredTextField(int maxChars, Pattern validator) {
        UnaryOperator<TextFormatter.Change> filter = input -> {
            String newText = input.getControlNewText();
            Matcher matcher = validator.matcher(newText);
            if (!matcher.matches() || newText.length() > maxChars) {
                input.setText("");
            }
            return input;
        };
        setTextFormatter(new TextFormatter<TextFormatter.Change>(filter));
    }
}