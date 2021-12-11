package com.team2363.helixnavigator.document;

import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class HBasePathElement extends HPathElement {

    private final BooleanProperty shown = new SimpleBooleanProperty(this, "shown", true);
    public HBasePathElement() {

    }

    public final BooleanProperty shownProperty() {
        return shown;
    }

    public final void setShown(boolean value) {
        shown.set(value);
    }

    @SerializedJSONObjectValue(key = "shown")
    public final boolean getShown() {
        return shown.get();
    }
}