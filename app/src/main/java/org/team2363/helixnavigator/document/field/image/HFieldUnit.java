package org.team2363.helixnavigator.document.field.image;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.quantity.Length;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;

import si.uom.SI;
import systems.uom.common.USCustomary;

import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;

@JSONSerializable(JSONType.STRING)
@JSONDeserializable({JSONType.STRING})
public class HFieldUnit {
    
    @SerializedJSONEntry
    public final String unitName;

    public final Unit<Length> unit;

    @DeserializedJSONConstructor
    public HFieldUnit(@DeserializedJSONEntry String unitName) {
        this.unitName = unitName;

        // WPILib docs:
        // "The field units are case-insensitive and can be in meters, cm,
        // mm, inches, feet, yards, or miles. Singular, plural, and
        // abbreviations are supported (e.g. “meter”,”meters”, and ”m” are
        // all valid for specifying meters)"
        switch (unitName.trim().toLowerCase()) {
            default:
            case "meter":
            case "m":
            case "meters":
                this.unit =  SI.METRE;
                break;
            case "centimeter":
            case "cm":
            case "centimeters":
                this.unit = MetricPrefix.CENTI(SI.METRE);
                break;
            case "millimeter":
            case "mm":
            case "millimeters":
                this.unit = MetricPrefix.MILLI(SI.METRE);
                break;
            case "inch":
            case "in":
            case "inches":
                this.unit = USCustomary.INCH;
                break;
            case "foot":
            case "ft":
            case "feet":
                this.unit = USCustomary.FOOT;
                break;
            case "yard":
            case "yd":
            case "yards":
                this.unit = USCustomary.YARD;
                break;
            case "mile":
            case "mi":
            case "miles":
                this.unit = USCustomary.MILE;
                break;
        }
    }
}
