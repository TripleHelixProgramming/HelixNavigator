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
package org.team2363.helixnavigator.document;

import java.io.File;
import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.global.Standards;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * This class represents a document in Helix Navigator
 * 
 * @author Justin Babilino
 */
@JSONSerializable
public class HDocument { // TODO: work on encapsulation of fields

    /**
     * The field that contains the image that the Editor pane will display behind
     * the path editor.
     */
    private final ObjectProperty<HFieldImage> fieldImage = new SimpleObjectProperty<>(this, "fieldImage", Standards.DEFAULT_FIELD_IMAGE);
    /**
     * The list of paths in this document. A path can be selected to be displayed in
     * the Editor pane.
     */
    private final ObservableList<HPath> paths = FXCollections.<HPath>observableArrayList();
    /**
     * The property containing the currently selected path index. If this value is
     * set to <code>-1</code>, then no path is selected, and there are no paths in
     * the document. The currently selected path will be displayed and editable in
     * the Editor pane.
     */
    private final IntegerProperty selectedPathIndex = new SimpleIntegerProperty(this, "selectedPathIndex", -1);
    private final ReadOnlyObjectWrapper<HPath> selectedPath = new ReadOnlyObjectWrapper<>(this, "selectedPath", null);
    /**
     * The file path that this document should be saved to
     */
    private final ObjectProperty<File> saveLocation = new SimpleObjectProperty<>(this, "saveLocation", null);
    /**
     * the property representing whether or not this document has been saved in its
     * entirety to a file
     */
    private final BooleanProperty savedProperty = new SimpleBooleanProperty(this, "saved", false); // change to true later when state management is added

    @DeserializedJSONConstructor
    public HDocument() {
        paths.addListener((ListChangeListener.Change<? extends HPath> change) -> {
            if (paths.size() == 0) {
                setSelectedPathIndex(-1);
            }
        });
        selectedPathIndex.addListener((currentIndex, oldIndex, newIndex) -> {
            if (newIndex.intValue() >= 0 && newIndex.intValue() < paths.size()) {
                setSelectedPath(paths.get(newIndex.intValue()));
            } else {
                setSelectedPath(null);
            }
        });
    }

    public final ObjectProperty<HFieldImage> fieldImageProperty() {
        return fieldImage;
    }

    @DeserializedJSONTarget
    public final void setFieldImage(@DeserializedJSONObjectValue(key = "field_image") HFieldImage value) {
        fieldImage.set(value);
    }

    @SerializedJSONObjectValue(key = "field_image")
    public final HFieldImage getFieldImage() {
        return fieldImage.get();
    }

    @DeserializedJSONTarget
    public final void setPaths(@DeserializedJSONObjectValue(key = "paths") List<? extends HPath> newPaths) {
        paths.setAll(newPaths);
    }

    @SerializedJSONObjectValue(key = "paths")
    public final ObservableList<HPath> getPaths() {
        return paths;
    }

    public final IntegerProperty selectedPathIndexProperty() {
        return selectedPathIndex;
    }

    @DeserializedJSONTarget
    public final void setSelectedPathIndex(@DeserializedJSONObjectValue(key = "selected_path_index") int value) {
        selectedPathIndex.set(value);
    }

    @SerializedJSONObjectValue(key = "selected_path_index")
    public final int getSelectedPathIndex() {
        return selectedPathIndex.get();
    }

    public final ReadOnlyObjectProperty<HPath> selectedPathProperty() {
        return selectedPath.getReadOnlyProperty();
    }

    private final void setSelectedPath(HPath value) {
        selectedPath.set(value);
    }

    public final HPath getSelectedPath() {
        return selectedPath.get();
    }

    public final boolean isPathSelected() {
        return getSelectedPathIndex() >= 0;
    }

    public final ObjectProperty<File> saveLocationProperty() {
        return saveLocation;
    }

    public void setSaveLocation(File value) {
        saveLocation.set(value);
    }

    public File getSaveLocation() {
        return saveLocation.get();
    }

    public boolean isSaveLocationSet() {
        return getSaveLocation() != null;
    }

    public BooleanProperty savedProperty() {
        return savedProperty;
    }

    public void setSaved(boolean value) {
        savedProperty.set(value);
    }

    public boolean isSaved() {
        return savedProperty.get();
    }
}