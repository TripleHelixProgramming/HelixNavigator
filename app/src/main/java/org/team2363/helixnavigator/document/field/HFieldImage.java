package org.team2363.helixnavigator.document.field;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONString;
import com.jlbabilino.json.TypeMarker;

import javafx.scene.image.Image;

public interface HFieldImage {

    // still not quite sure why i can't make this private
    static final TypeMarker<HFieldImageDefault> DEFAULT_REFERENCE_TYPE = new TypeMarker<>() {};
    static final TypeMarker<HFieldImageCustom> CUSTOM_REFERENCE_TYPE = new TypeMarker<>() {};

    public static enum ReferenceType {
        DEFAULT,
        CUSTOM;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public String getName();

    public ReferenceType getReferenceType();

    public Image getImage();

    @DeserializedJSONDeterminer
    public static TypeMarker<? extends HFieldImage> determiner(JSONObject jsonObject) throws JSONDeserializerException {
        if (!jsonObject.containsKey("reference_type")) {
            throw new JSONDeserializerException("Field image reference does not contain key \"reference_type\".");
        }
        JSONEntry entry = jsonObject.get("reference_type");
        if (!entry.isString()) {
            throw new JSONDeserializerException("Entry at key \"reference_type\" in field image is not a JSONString.");
        }
        String referenceTypeString = ((JSONString) entry).getString();
        switch (referenceTypeString) {
            case "default":
                return DEFAULT_REFERENCE_TYPE;
            case "custom":
                return CUSTOM_REFERENCE_TYPE;
            default:
                throw new JSONDeserializerException("Reference type in field image was not recognized; expecting \"default\" or \"custom\", but got \"" + referenceTypeString + "\".");
        }
    }
}