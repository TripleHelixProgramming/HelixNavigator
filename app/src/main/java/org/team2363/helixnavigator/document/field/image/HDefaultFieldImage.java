package org.team2363.helixnavigator.document.field.image;

import java.io.InputStream;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

import org.team2363.helixnavigator.global.DefaultFieldImages;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.scene.image.Image;
import si.uom.SI;
import tech.units.indriya.quantity.Quantities;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HDefaultFieldImage implements HFieldImage {

    @SerializedJSONObjectValue(key = "game")
    public final String game;
    @SerializedJSONObjectValue(key = "field-image")
    public final String fieldImage;
    @SerializedJSONObjectValue(key = "field-corners")
    public final HFieldCorners fieldCorners;
    @SerializedJSONObjectValue(key = "field-size")
    public final HFieldSize fieldSize;
    @SerializedJSONObjectValue(key = "field-unit")
    public final HFieldUnit fieldUnit;

    private final Image image;
    private final double imageRes;
    private final double imageCenterX;
    private final double imageCenterY;

    @DeserializedJSONConstructor
    public HDefaultFieldImage(
            @DeserializedJSONObjectValue(key = "game") String game,
            @DeserializedJSONObjectValue(key = "field-image") String fieldImage,
            @DeserializedJSONObjectValue(key = "field-corners") HFieldCorners fieldCorners,
            @DeserializedJSONObjectValue(key = "field-size") HFieldSize fieldSize,
            @DeserializedJSONObjectValue(key = "field-unit") HFieldUnit fieldUnit) {
        this.game = game;
        this.fieldImage = fieldImage;
        this.fieldCorners = fieldCorners;
        this.fieldSize = fieldSize;
        this.fieldUnit = fieldUnit;

        InputStream imageStream = DefaultFieldImages.class.getResourceAsStream("wpifieldimages/" + this.fieldImage);
        if (imageStream == null) {
            imageStream = DefaultFieldImages.class.getResourceAsStream("extrafieldimages/" + this.fieldImage);
        }
        this.image = new Image(imageStream);
        
        // Assume the image scales proportionally on both axes
        double fieldAreaWidthPx = fieldCorners.bottomRightCorner.x - fieldCorners.topLeftCorner.x;
        Quantity<Length> fieldAreaWidthUnits = Quantities.getQuantity(fieldSize.width, fieldUnit.unit);
        this.imageRes = fieldAreaWidthUnits.to(SI.METRE).getValue().doubleValue() / fieldAreaWidthPx;

        this.imageCenterX = fieldCorners.topLeftCorner.x * imageRes;
        this.imageCenterY = fieldCorners.bottomRightCorner.y * imageRes;
    }

    @Override
    public String getName() {
        return game;
    }

    @Override
    public double getImageRes() {
        return imageRes;
    }

    @Override
    public double getImageCenterX() {
        return imageCenterX;
    }

    @Override
    public double getImageCenterY() {
        return imageCenterY;
    }

    @Override
    public Image getImage() {
        return image;
    }
}
