package org.team2363.helixnavigator.document.field.image;

import java.io.InputStream;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import org.team2363.helixnavigator.global.Standards;

import javafx.scene.image.Image;

@JSONSerializable
public class HDefaultFieldImage implements HFieldImage {

    private final String name;
    private final double imageRes;
    private final double imageCenterX;
    private final double imageCenterY;
    private final String fileName;
    private final Image image;

    @DeserializedJSONConstructor
    public HDefaultFieldImage(
            @DeserializedJSONObjectValue(key = "name") String name, 
            @DeserializedJSONObjectValue(key = "image_res") double imageRes,
            @DeserializedJSONObjectValue(key = "image_center_x") double imageCenterX,
            @DeserializedJSONObjectValue(key = "image_center_y") double imageCenterY,
            @DeserializedJSONObjectValue(key = "file_name") String fileName) {
        this.name = name;
        this.imageRes = imageRes;
        this.imageCenterX = imageCenterX;
        this.imageCenterY = imageCenterY;
        this.fileName = fileName;
        InputStream imageStream = HDefaultFieldImage.class.getResourceAsStream(Standards.FIELD_IMAGES_PATH_PREFIX + this.fileName);
        image = new Image(imageStream);
    }

    @SerializedJSONObjectValue(key = "name")
    @Override
    public String getName() {
        return name;
    }

    @SerializedJSONObjectValue(key = "image_res")
    @Override
    public double getImageRes() {
        return imageRes;
    }

    @SerializedJSONObjectValue(key = "image_center_x")
    @Override
    public double getImageCenterX() {
        return imageCenterX;
    }

    @SerializedJSONObjectValue(key = "image_center_y")
    @Override
    public double getImageCenterY() {
        return imageCenterY;
    }

    @SerializedJSONObjectValue(key = "file_name")
    public String getFileName() {
        return fileName;
    }

    @Override
    public Image getImage() {
        return image;
    }
}
