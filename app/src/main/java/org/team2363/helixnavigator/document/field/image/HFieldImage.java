package org.team2363.helixnavigator.document.field.image;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.JSONObject;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.TypeMarker;

import javafx.scene.image.Image;

@JSONSerializable
public interface HFieldImage {

    static final TypeMarker<HReferenceFieldImage> REFERENCE_TYPE = new TypeMarker<>() {};

    public String getName();
    public double getImageRes();
    public double getImageCenterX();
    public double getImageCenterY();
    public Image getImage();

    @DeserializedJSONDeterminer
    public static TypeMarker<? extends HFieldImage> determiner(JSONObject jsonObject) {
        return REFERENCE_TYPE;
    }
}