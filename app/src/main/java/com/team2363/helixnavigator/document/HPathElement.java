package com.team2363.helixnavigator.document;

import com.team2363.lib.json.JSONSerializable;
import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@JSONSerializable
public abstract class HPathElement {
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    protected HPathElement() {
    }

    public abstract void translateRelativeX(double x);

    public abstract void translateRelativeY(double y);

    public final BooleanProperty selectedProperty() {
        return selected;
    }

    public final void setSelected(boolean value) {
        selected.set(value);
    }

    @SerializedJSONObjectValue(key = "selected")
    public final boolean getSelected() {
        return selected.get();
    }
}
