package org.team2363.helixnavigator;

import javax.measure.Quantity;
import javax.measure.quantity.Force;

import org.team2363.lib.unit.CustomUnits;

import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

public class Test {
    public static void main(String[] args) {
        // Unit<Mass> lb = USCustomary.POUND;
        // Unit<Mass> g = Units.GRAM;
        // var tenLbs = Quantities.getQuantity(10.0, lb);
        // var asG = tenLbs.to(g);
        // System.out.println("Ten pounds: " + tenLbs);
        // System.out.println("As Grams:   " + asG);

        // Unit<Length> inch = USCustomary.INCH;
        // Unit<Length> centimeter = MetricPrefix.CENTI(USCustomary.METER);
        // var oneInch = Quantities.getQuantity(10.0, inch);
        // var asCm = oneInch.to(centimeter);
        // System.out.println("One inch:      " + oneInch.getValue() + " "  + oneInch.getUnit().getSymbol());
        // System.out.println("As centimeter: " + asCm);
        // System.out.println(CustomUnits.KILOGRAM_SQUARE_METRE);
        // System.out.println(CustomUnits.NEWTON_METRE);
        // Quantity<Acceleration> x = Quantities.getQuantity(10.0, CustomUnits.FOOT_PER_SQUARE_SECOND);
        // System.out.println(x);
        Quantity<Force> onePoundForce = Quantities.getQuantity(1.0, CustomUnits.POUND_FORCE);
        System.out.println(CustomUnits.POUND_FOOT);
    }
}
