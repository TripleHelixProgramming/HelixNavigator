package org.team2363.helixnavigator.ui.prompts;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;

public class StrictStringPrompt extends TextInputDialog {

    /**
     * The maximum number of characters allowed to be submitted in the text field
     */
    private int maxChars;
    /**
     * The regex pattern used to validate the string
     */
    private Pattern validator;

    public StrictStringPrompt(String message, String hint, int maxChars, Pattern validator) {
        setHeaderText(message);
        getEditor().setPromptText(hint);
        this.maxChars = maxChars;
        this.validator = validator;

        UnaryOperator<TextFormatter.Change> filter = input -> {
            String newText = input.getControlNewText();
            Matcher matcher = this.validator.matcher(newText);
            if (!matcher.matches() || newText.length() > this.maxChars) {
                input.setText("");
            }
            return input;
        };
        getEditor().setTextFormatter(new TextFormatter<TextFormatter.Change>(filter));
    }

    public StrictStringPrompt(String message, String hint) {
        this(message, hint, Integer.MAX_VALUE, Pattern.compile(".*"));
    }

    public StrictStringPrompt() {
        this("Enter valid text here", "enter text");
    }
}
