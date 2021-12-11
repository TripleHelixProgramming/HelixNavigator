/*
 * Copyright (C) 2021 Triple Helix Robotics - FRC Team 2363
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
package com.team2363.helixnavigator.ui.prompts;

import java.util.regex.Pattern;

/**
 * This type of <code>StrictStringPrompt</code> has a text field to enter a
 * decimal value. This class does not have any methods for retrieving the
 * decimal value, but the parser methods of Double, Float, and BigDecimal can be
 * used to retrive the decimal value. This could be implemented with a
 * <code>NumberFormat</code>, if you have ideas for how that could work, feel
 * free to try it and submit a PR on GitHub.
 * 
 * @author Justin Babilino
 */
public class DecimalPrompt extends StrictStringPrompt {

    /**
     * This regex pattern will match with <code>String str</code> IF AND ONLY IF
     * <code>Double.parseDouble(str)</code> does not throw a
     * <code>NumberFormatException</code>.
     */
    private final Pattern DECIMAL_VALIDATOR = Pattern.compile(" *(\\d+\\.?\\d*|\\.\\d+) *");

    /**
     * Constructs a <code>DecimalPrompt</code> with a message and a hint.
     * @param message message displayed to user
     * @param hint prompt text shown in text field when out of focus
     */
    public DecimalPrompt(String message, String hint) {
        super(message, hint);
        setMaxChars(Integer.MAX_VALUE);
        setValidator(DECIMAL_VALIDATOR);
    }

    /**
     * Constructs a <code>DecimalPrompt</code> with a default message of <code>"Enter a decimal value"</code> and a hint of <code>"enter number"</code>.
     */
    public DecimalPrompt() {
        this("Enter a decimal value", "enter number");
    }
}
