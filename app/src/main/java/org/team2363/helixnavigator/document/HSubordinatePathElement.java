package org.team2363.helixnavigator.document;

import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.JSONSerializerException;

import javafx.scene.transform.Transform;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public abstract class HSubordinatePathElement extends HSelectableElement {

    public abstract void transformRelative(Transform transform);

    public abstract void translateRelativeX(double x);

    public abstract void translateRelativeY(double y);

    public void translateRelative(double x, double y) {
        translateRelativeX(x);
        translateRelativeY(y);
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
