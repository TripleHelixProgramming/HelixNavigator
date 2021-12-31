package org.team2363.helixnavigator.ui.prompts;

import java.util.regex.Pattern;

public class DecimalPrompt extends StrictStringPrompt {

    public DecimalPrompt(String message, String hint) {
        super(message, hint, Integer.MAX_VALUE, Pattern.compile(" *(\\d+\\.?\\d*|\\.\\d+) *"));
    }

    public DecimalPrompt() {
        this("Enter a decimal value", "enter number");
    }
}
