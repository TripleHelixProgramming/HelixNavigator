/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.team2363.helixnavigator.ui.prompts;

import java.util.regex.Pattern;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 * This class is a type of <code>TextInputDialog</code> that implements data
 * validation on the text field.
 * 
 * @author Justin Babilino
 */
public class StrictStringPrompt extends TextInputDialog {

    /**
     * This stores a reference to this dialog's "OK" button. This allows for the
     * button to be disabled when the text is not valid.
     */
    private final Button okButton;
    /**
     * The maximum amount of characters allowed to be submitted in the text field
     */
    private int maxChars;
    /**
     * The regex pattern used to validate the string
     */
    private Pattern validator;

    /**
     * Constructs a <code>StrictStringPrompt</code> with a message, hint, max
     * characters, and validator.
     * 
     * @param message   message displayed on prompt
     * @param hint      prompt text shown in text field when text field is out of
     *                  focus
     * @param maxChars  maximum amount of characters allowed in string validation
     * @param validator regex used for validation of text entered into the text
     *                  field
     */
    public StrictStringPrompt(String message, String hint, int maxChars, Pattern validator) {
        setMessage(message);
        setHint(hint);
        setMaxChars(maxChars);
        setValidator(validator);

        getEditor().textProperty().addListener(this::textFieldChanged);

        okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
    }

    /**
     * Constructs a <code>StrictStringPrompt</code> with a message and hint. By
     * default, the max characters allowed will be 50 and the validation will allow
     * all characters except newline characters.
     * 
     * @param message message displayed on prompt
     * @param hint    prompt text shown in text field when text field is out of
     *                focus
     */
    public StrictStringPrompt(String message, String hint) {
        this(message, hint, 50, Pattern.compile(".*"));
    }

    /**
     * Constructs a <code>StrictStringPrompt</code>. By default, the message will be
     * <code>"Enter valid text here"</code>, the hint will be
     * <code>"enter text"</code>, the max characters allowed will be 50, and the
     * validation will allow all characters except newline characters.
     */
    public StrictStringPrompt() {
        this("Enter valid text here", "enter text");
    }

    /**
     * This listener event is triggered whenever the user changes the content of the
     * text field. It tests the validity of the text and enables or disables the
     * "OK" button accordingly.
     * 
     * @param currentVal an observable value containing the current value of the
     *                   text in the text field
     * @param oldVal     the value of the text in the text field before the text was
     *                   modified
     * @param newVal     the value of the text field after the text was modified
     */
    private void textFieldChanged(ObservableValue<? extends String> currentVal, String oldVal, String newVal) {
        okButton.setDisable(newVal == null || newVal.length() < 1 || newVal.length() > maxChars
                || !validator.matcher(newVal).matches());
    }

    /**
     * @param message the new message
     */
    public void setMessage(String message) {
        setHeaderText(message);
    }

    /**
     * @return the current message
     */
    public String getMessage() {
        return getHeaderText();
    }

    /**
     * @param hint the new hint
     */
    public void setHint(String hint) {
        getEditor().setPromptText(hint);
    }

    /**
     * @return the current hint
     */
    public String getHint() {
        return getEditor().getPromptText();
    }

    /**
     * @param maxChars the new max chars
     */
    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
    }

    /**
     * @return the current max chars
     */
    public int getMaxChars() {
        return maxChars;
    }

    /**
     * @param validator the new validator
     */
    public void setValidator(Pattern validator) {
        this.validator = validator;
    }

    /**
     * @return the current validator
     */
    public Pattern getValidator() {
        return validator;
    }
}
