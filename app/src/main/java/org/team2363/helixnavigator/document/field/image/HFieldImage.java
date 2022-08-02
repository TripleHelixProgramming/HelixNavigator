package org.team2363.helixnavigator.document.field.image;

import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;

import javafx.scene.image.Image;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public interface HFieldImage {

    public String getName();
    public double getImageRes();
    public double getImageCenterX();
    public double getImageCenterY();
    public Image getImage();

    @DeserializedJSONDeterminer
    public static Class<? extends HFieldImage> determiner() throws JSONDeserializerException {
        return HReferenceFieldImage.class;
    }
}