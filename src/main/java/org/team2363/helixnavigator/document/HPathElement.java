package org.team2363.helixnavigator.document;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JSONSerializable
public abstract class HPathElement {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ReadOnlyBooleanWrapper selected = new ReadOnlyBooleanWrapper(this, "selected", false);

    protected HPathElement() {
    }

    public abstract void translateRelativeX(double x);

    public abstract void translateRelativeY(double y);

    public void translateRelative(double x, double y) {
        translateRelativeX(x);
        translateRelativeY(y);
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

    public final ReadOnlyBooleanProperty selectedProperty() {
        return selected.getReadOnlyProperty();
    }

    final void setSelected(boolean value) {
        selected.set(value);
    }

    public final boolean isSelected() {
        return selected.get();
    }

    @Override
    public String toString() {
        return JSONSerializer.serializeString(this);
    }
}
