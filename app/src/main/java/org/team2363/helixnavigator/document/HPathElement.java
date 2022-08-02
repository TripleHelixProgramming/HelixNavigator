package org.team2363.helixnavigator.document;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.JSONSerializerException;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
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
        try {
            return JSONSerializer.serializeString(this);
        } catch (InvalidJSONTranslationConfiguration | JSONSerializerException e) {
            return "";
        }
    }
}