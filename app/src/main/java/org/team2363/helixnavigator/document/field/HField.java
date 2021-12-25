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
package org.team2363.helixnavigator.document.field;

import org.team2363.helixnavigator.document.obstacle.HObstacle;

import java.util.List;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

@JSONSerializable
public class HField {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ObjectProperty<HFieldImage> fieldImage = new SimpleObjectProperty<>(this, "field", null);
    private final DoubleProperty fieldImageRes = new SimpleDoubleProperty(this, "imageRes", 0.0);
    private final DoubleProperty fieldImageCenterX = new SimpleDoubleProperty(this, "imageCenterX", 0.0);
    private final DoubleProperty fieldImageCenterY = new SimpleDoubleProperty(this, "imageCenterY", 0.0);
    private final BooleanProperty imageShown = new SimpleBooleanProperty(this, "imageShown", true);
    private final ObservableList<HObstacle> obstacles = FXCollections.<HObstacle>observableArrayList();

    public HField() {
    }

    public final StringProperty nameProperty() {
        return name;
    }

    @DeserializedJSONTarget
    public final void setName(@DeserializedJSONObjectValue(key = "name") String value) {
        name.set(value);
    }

    @SerializedJSONObjectValue(key = "name")
    public final String getName() {
        return name.get();
    }

    public final DoubleProperty imageResProperty() {
        return imageRes;
    }

    @DeserializedJSONTarget
    public final void setImageRes(@DeserializedJSONObjectValue(key = "image_res") double value) {
        imageRes.set(value);
    }

    @SerializedJSONObjectValue(key = "image_res")
    public final double getImageRes() {
        return imageRes.get();
    }

    public final DoubleProperty imageCenterXProperty() {
        return imageCenterX;
    }

    @DeserializedJSONTarget
    public final void setImageCenterX(@DeserializedJSONObjectValue(key = "image_center_x") double value) {
        imageCenterX.set(value);
    }

    @SerializedJSONObjectValue(key = "image_center_x")
    public final double getImageCenterX() {
        return imageCenterX.get();
    }

    public final DoubleProperty imageCenterYProperty() {
        return imageCenterY;
    }

    @DeserializedJSONTarget
    public final void setImageCenterY(@DeserializedJSONObjectValue(key = "image_center_y") double value) {
        imageCenterY.set(value);
    }

    @SerializedJSONObjectValue(key = "image_center_y")
    public final double getImageCenterY() {
        return imageCenterY.get();
    }

    public final BooleanProperty fieldImageShownProperty() {
        return imageShown;
    }

    @DeserializedJSONTarget
    public final void setImageShown(@DeserializedJSONObjectValue(key = "image_shown") boolean value) {
        imageShown.set(value);
    }

    @SerializedJSONObjectValue(key = "image_shown")
    public final boolean getFieldImageShown() {
        return imageShown.get();
    }

    @DeserializedJSONTarget
    public final void setObstacles(@DeserializedJSONObjectValue(key = "obstacles") List<? extends HObstacle> newObstacles) {
        obstacles.setAll(newObstacles);
    }

    @SerializedJSONObjectValue(key = "obstacles")
    public final ObservableList<HObstacle> getObstacles() {
        return obstacles;
    }
}