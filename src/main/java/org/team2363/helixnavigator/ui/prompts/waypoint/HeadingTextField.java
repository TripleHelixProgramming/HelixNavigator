package org.team2363.helixnavigator.ui.prompts.waypoint;

import java.util.function.UnaryOperator;

import org.team2363.helixnavigator.global.Standards;
import org.team2363.lib.ui.validation.MathExpressionTextField;

public class HeadingTextField extends MathExpressionTextField {

    private static final UnaryOperator<Double> transformation = new UnaryOperator<Double>() {
        @Override
        public Double apply(Double input) {
            if (Math.abs(input) >= 1E15) {
                return 0.0;
            }
            int factor = (int) input.doubleValue() / 360;
            input = input - 360 * factor;
            if (input <= Standards.MIN_HEADING) {
                return apply(input + 360);
            } else if (input > Standards.MAX_HEADING) {
                return apply(input - 360);
            } else {
                return input;
            }
        }
    };

    public HeadingTextField() {
        setInputTransformation(transformation);
    }
}