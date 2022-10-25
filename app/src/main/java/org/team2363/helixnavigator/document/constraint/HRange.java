package org.team2363.helixnavigator.document.constraint;

import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONEntry.JSONType;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HRange {
    
    private final DoubleProperty min = new SimpleDoubleProperty(this, "min", Double.NEGATIVE_INFINITY);
    private final DoubleProperty max = new SimpleDoubleProperty(this, "max", Double.POSITIVE_INFINITY);

    public HRange() {
    }

    public DoubleProperty minProperty() {
        return min;
    }
    public void setMin(double value) {
        min.set(value);
    }
    public double getMin() {
        return min.get();
    }

    public DoubleProperty maxProperty() {
        return max;
    }
    public void setMax(double value) {
        max.set(value);
    }
    public double getMax() {
        return max.get();
    }
}