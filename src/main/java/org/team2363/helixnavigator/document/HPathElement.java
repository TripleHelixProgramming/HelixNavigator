package org.team2363.helixnavigator.document;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JSONSerializable
public abstract class HPathElement extends HSelectableElement {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");

    protected HPathElement() {
    }

    public final StringProperty nameProperty() {
        return name;
    }

    @DeserializedJSONTarget
    public final void setName(@DeserializedJSONObjectValue(key = "name") String value) {
        name.set(value);
    }

    @SerializedJSONObjectValue(key = "name")
    public final String getName() {
        return name.get();
    }

    @Override
    public String toString() {
        return JSONSerializer.serializeString(this);
    }
}