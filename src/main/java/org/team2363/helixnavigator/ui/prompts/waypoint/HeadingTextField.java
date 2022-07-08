package org.team2363.helixnavigator.ui.prompts.waypoint;

import java.util.function.UnaryOperator;

import javax.measure.quantity.Angle;

import org.team2363.helixnavigator.global.Standards;
import org.team2363.helixnavigator.global.Standards.ExportedUnits;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngle;
import org.team2363.lib.ui.validation.UnitTextField;

public class HeadingTextField extends UnitTextField<Angle> {

    private static final UnaryOperator<Double> transformation = new UnaryOperator<Double>() {
        @Override
        public Double apply(Double input) {
            // if (Math.abs(input) >= 1E15) {
            //     return 0.0;
            // }
            // int factor = (int) (input.doubleValue() / (2*Math.PI));
            // input = input - (2*Math.PI) * factor;
            // if (input <= Standards.MIN_HEADING) {
            //     return apply(input + (2*Math.PI));
            // } else if (input > Standards.MAX_HEADING) {
            //     return apply(input - (2*Math.PI));
            // } else {
            //     return input;
            // }
            return input;
        }
    };

    public HeadingTextField() {
        super(ExportedUnits.ANGLE_UNIT, SupportedAngle.UNITS);
        setInputTransformation(transformation);
    }
}