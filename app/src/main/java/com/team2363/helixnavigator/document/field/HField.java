/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team2363.helixnavigator.document.field;

import com.team2363.helixnavigator.document.obstacle.HAbstractObstacle;
import com.jlbabilino.json.JSONEntry;
import com.team2363.lib.json.JSONSerializable;
import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a specific robot playing field. It contains a list of
 * obstacles, which represent the solid objects and boundries on the field. It
 * also contains an image of the field, a pixel per unit measurement, and a
 * name.
 * 
 * @author Justin Babilino
 */
@JSONSerializable(rootType = JSONEntry.Type.OBJECT)
public class HField {

    /**
     * The string property for the name of this path
     */
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    /**
     * The resolution of the image imported in pixels per unit (Helix Navigator is
     * unit independent, meaning that this is whatever unit you use in the whole
     * system. For FIRST this will likely be ANSI units like inch, pound, etc.)
     */
    private final DoubleProperty imageRes = new SimpleDoubleProperty(this, "imageRes", 0.0);
    /**
     * The property for the x offset of the center of the image. This is measured in
     * pixels, please take the field image and measure the amount of pixels from the
     * top left corner to the pixel you desire to be the center.
     */
    private final DoubleProperty xImageOffset = new SimpleDoubleProperty(this, "xImageOffset", 0.0);
    /**
     * The property for the y offset of the center of the image
     */
    private final DoubleProperty yImageOffset = new SimpleDoubleProperty(this, "yImageOffset", 0.0);
    /**
     * The property for the field image as a base 64 encoded string
     */
    private final StringProperty fieldImageB64 = new SimpleStringProperty(this, "fieldImage", "");
    /**
     * The property indicating whether or not the field image should be displayed
     */
    private final BooleanProperty fieldImageShown = new SimpleBooleanProperty(this, "fieldImageShown", true);
    /**
     * The list of obstacles that are a part of the field
     */
    private final ObservableList<HAbstractObstacle> obstacles = FXCollections.<HAbstractObstacle>observableArrayList();

    /**
     * Constructs an <code>HField</code> with no properties set.
     */
    public HField() {
    }

    /**
     * @return the name property
     */
    public final StringProperty nameProperty() {
        return name;
    }

    /**
     * @param value the new name
     */
    public final void setName(String value) {
        name.set(value);
    }

    /**
     * @return the current name
     */
    @SerializedJSONObjectValue(key = "name")
    public final String getName() {
        return name.get();
    }

    /**
     * @return the image resolution property
     */
    public final DoubleProperty imageResProperty() {
        return imageRes;
    }

    /**
     * @param value the new image resolution
     */
    public final void setImageRes(double value) {
        imageRes.set(value);
    }

    /**
     * @return the current image resolution
     */
    @SerializedJSONObjectValue(key = "image_res")
    public final double getImageRes() {
        return imageRes.get();
    }

    /**
     * @return the x image offset property
     */
    public final DoubleProperty xImageOffsetProperty() {
        return xImageOffset;
    }

    /**
     * @param value the new x image offset
     */
    public final void setXImageOffset(double value) {
        xImageOffset.set(value);
    }

    /**
     * @return the current x image offset
     */
    @SerializedJSONObjectValue(key = "x_image_offset")
    public final double getXImageOffset() {
        return xImageOffset.get();
    }

    /**
     * @return the y image offset property
     */
    public final DoubleProperty yImageOffsetProperty() {
        return yImageOffset;
    }

    /**
     * @param value the new y image offset
     */
    public final void setYImageOffset(double value) {
        yImageOffset.set(value);
    }

    /**
     * @return the current y image offset
     */
    @SerializedJSONObjectValue(key = "y_image_offset")
    public final double getYImageOffset() {
        return yImageOffset.get();
    }

    /**
     * @return the field image base 64 property
     */
    public final StringProperty fieldImageB64Property() {
        return fieldImageB64;
    }

    /**
     * @param value the new field image base 64
     */
    public final void setFieldImageB64(String value) {
        fieldImageB64.set(value);
    }

    /**
     * @return the current field image base 64
     */
    @SerializedJSONObjectValue(key = "image_b64")
    public final String getFieldImageB64() {
        return fieldImageB64.getValue();
    }

    public final BooleanProperty fieldImageShownProperty() {
        return fieldImageShown;
    }
    public final void setFieldImageShown(boolean value) {
        fieldImageShown.set(value);
    }
    public final boolean getFieldImageShown() {
        return fieldImageShown.get();
    }
    
    /**
     * @return the list of obstacles
     */
    @SerializedJSONObjectValue(key = "obstacles")
    public final ObservableList<HAbstractObstacle> getObstacles() {
        return obstacles;
    }
}
