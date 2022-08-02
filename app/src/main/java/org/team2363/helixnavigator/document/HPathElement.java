package org.team2363.helixnavigator.document;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class HPathElement extends HSubordinatePathElement implements HNamedElement {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");

    @Override
    public final StringProperty nameProperty() {
        return name;
    }

    @DeserializedJSONTarget
    @Override
    public final void setName(@DeserializedJSONObjectValue(key = "name") String value) {
        name.set(value);
    }

    @SerializedJSONObjectValue(key = "name")
    @Override
    public final String getName() {
        return name.get();
    }
}