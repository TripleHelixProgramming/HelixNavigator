package org.team2363.helixnavigator.ui.prompts.waypoint;

import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.prompts.DecimalTextField;

public class HeadingTextField extends DecimalTextField {

    @Override
    protected double transformInput(double input) {
        if (input <= Standards.MIN_HEADING) {
            return transformInput(input + 360);
        } else if (input > Standards.MAX_HEADING) {
            return transformInput(input - 360);
        } else {
            return input;
        }
    }
}
