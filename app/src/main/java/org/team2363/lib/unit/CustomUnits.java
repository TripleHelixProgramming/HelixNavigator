package org.team2363.lib.unit;

import javax.measure.Unit;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Force;

import si.uom.NonSI;
import si.uom.quantity.AngularSpeed;
import si.uom.quantity.Torque;
import systems.uom.common.USCustomary;
import systems.uom.unicode.CLDR;
import tech.units.indriya.function.AddConverter;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.unit.AlternateUnit;
import tech.units.indriya.unit.ProductUnit;
import tech.units.indriya.unit.TransformedUnit;
import tech.units.indriya.unit.Units;

public class CustomUnits {

    public static final Unit<Force> POUND_FORCE = new TransformedUnit<Force>(
            "lbf", "Pound-Force", Units.NEWTON, Units.NEWTON, MultiplyConverter.of(4.4482216152605));

    public static final Unit<Torque> NEWTON_METRE =
            new ProductUnit<>(Units.NEWTON.multiply(Units.METRE));

    public static final Unit<Torque> POUND_FOOT =
            new ProductUnit<>(POUND_FORCE.multiply(USCustomary.FOOT));

    public static final Unit<AngularSpeed> REVOLUTION_PER_MINUTE =
            new ProductUnit<>(NonSI.REVOLUTION.divide(Units.MINUTE));

    public static final Unit<AngularSpeed> DEGREE_ANGLE_PER_SECOND =
            new ProductUnit<>(NonSI.DEGREE_ANGLE.divide(Units.SECOND));

    public static final Unit<Acceleration> FOOT_PER_SQUARE_SECOND =
            new ProductUnit<>(USCustomary.FOOT.divide(Units.SECOND.pow(2)));

    public static final Unit<MomentOfInertia> KILOGRAM_SQUARE_METRE =
            new ProductUnit<>(Units.KILOGRAM.multiply(Units.SQUARE_METRE));

    public static final Unit<MomentOfInertia> POUND_SQUARE_INCH =
            new ProductUnit<>(USCustomary.POUND.multiply(USCustomary.INCH.pow(2)));
}