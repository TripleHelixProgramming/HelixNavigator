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

    /**
     * @return the name of the field image
     */
    public String getName();

    /**
     * @return the resolution of image, in meters per pixel
     */
    public double getImageRes();
    /**
     * @return the center x-coordinate of the image, in meters
     */
    public double getImageCenterX();
    /**
     * @return the center y-coordinate of
     */
    public double getImageCenterY();
    /**
     * @return the processed javafx image of the field
     */
    public Image getImage();

    @DeserializedJSONDeterminer
    public static Class<? extends HFieldImage> determiner() throws JSONDeserializerException {
        return HReferenceFieldImage.class;
    }
}