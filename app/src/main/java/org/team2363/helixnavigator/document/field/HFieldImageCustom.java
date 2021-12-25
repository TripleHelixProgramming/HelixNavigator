package org.team2363.helixnavigator.document.field;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.scene.image.Image;

@JSONSerializable
public class HFieldImageCustom implements HFieldImage {

    private final String name;
    private final String imageB64;
    private final Image image;

    @DeserializedJSONConstructor
    public HFieldImageCustom(@DeserializedJSONObjectValue(key = "name") String name,
            @DeserializedJSONObjectValue(key = "image_b64") String imageB64) {
        this.name = name;
        this.imageB64 = imageB64;
        byte[] decodedData = Base64.getDecoder().decode(this.imageB64);
        InputStream stream = new ByteArrayInputStream(decodedData);
        image = new Image(stream);
    }

    @SerializedJSONObjectValue(key = "reference_type")
    @Override
    public ReferenceType getReferenceType() {
        return ReferenceType.CUSTOM;
    }

    @SerializedJSONObjectValue(key = "name")
    @Override
    public String getName() {
        return name;
    }

    @SerializedJSONObjectValue(key = "image_b64")
    public String getImageB64() {
        return imageB64;
    }

    @Override
    public Image getImage() {
        return image;
    }
}