package org.team2363.helixnavigator.document.field.image;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

import org.team2363.helixnavigator.global.DefaultFieldImages;
import org.team2363.helixnavigator.global.DefaultResourceUnavailableException;

import javafx.scene.image.Image;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HReferenceFieldImage implements HFieldImage {

    private final HDefaultFieldImage fieldImage;

    @DeserializedJSONConstructor
    public HReferenceFieldImage(
            @DeserializedJSONObjectValue(key = "default") String name) throws DefaultResourceUnavailableException {
        fieldImage = DefaultFieldImages.forName(name);
    }

    @SerializedJSONObjectValue(key = "default")
    @Override
    public String getName() {
        return fieldImage.getName();
    }

    @Override
    public double getFieldAreaWidth() {
        return fieldImage.getFieldAreaWidth();
    }

    @Override
    public double getFieldAreaHeight() {
        return fieldImage.getFieldAreaHeight();
    }

    @Override
    public double getImageRes() {
        return fieldImage.getImageRes();
    }

    @Override
    public double getImageCenterX() {
        return fieldImage.getImageCenterX();
    }

    @Override
    public double getImageCenterY() {
        return fieldImage.getImageCenterY();
    }

    @Override
    public Image getImage() {
        return fieldImage.getImage();
    }
}