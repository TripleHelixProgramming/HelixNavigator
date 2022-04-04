package org.team2363.helixnavigator.ui.prompts.waypoint;

import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.validation.MathExpressionTextField;

public class HeadingTextField extends MathExpressionTextField {

    @Override
    protected double transformInput(double input) {
        if (Math.abs(input) >= 1E15) {
            return 0;
        }
        int factor = (int) input / 360;
        input = input - 360 * factor;
        if (input <= Standards.MIN_HEADING) {
            return transformInput(input + 360);
        } else if (input > Standards.MAX_HEADING) {
            return transformInput(input - 360);
        } else {
            return input;
        }
    }
}