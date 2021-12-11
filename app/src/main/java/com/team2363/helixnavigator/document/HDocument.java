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
package com.team2363.helixnavigator.document;

import java.io.File;

import com.team2363.helixnavigator.document.field.HField;
import com.team2363.lib.file.TextFileWriter;
import com.team2363.lib.json.JSON;
import com.team2363.lib.json.JSONEntry;
import com.team2363.lib.json.JSONSerializable;
import com.team2363.lib.json.JSONSerializer;
import com.team2363.lib.json.SerializedJSONObjectValue;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a document in Helix Navigator
 * 
 * @author Justin Babilino
 */
@JSONSerializable(rootType = JSONEntry.Type.OBJECT)
public class HDocument {

    /**
     * The field that contains the image that the Editor pane will display behind
     * the paths. This is not defined as a property because <code>HField</code> is
     * designed to be a mutable type that should never be reassigned.
     */
    private final HField field = new HField();
    /**
     * The list of paths in this document. A path can be selected to be displayed in
     * the Editor pane.
     */
    private final ObservableList<HPath> paths = FXCollections.<HPath>observableArrayList();
    /**
     * The property containing the currently selected path index. If this value is
     * set to <code>-1</code>, then no path is selected. The currently selected path
     * will be displayed and editable in the Editor pane.
     */
    private final IntegerProperty selectedPathIndex = new SimpleIntegerProperty(this, "selectedPathIndex", -1);
    /**
     * The file path that this document should be saved to
     */
    private final ObjectProperty<File> saveLocation = new SimpleObjectProperty<>(this, "saveLocation", null);
    /**
     * the property representing whether or not this document has been saved in its
     * entirety to a file
     */
    private final BooleanProperty savedProperty = new SimpleBooleanProperty(this, "saved", true);

    public HDocument() {
    }

    public final void save() {
        if (!isSaved()) {
            try {
                JSON json = JSONSerializer.serializeJSON(this);
                TextFileWriter writer = new TextFileWriter(getSaveLocation());
                writer.printString(json.exportJSON());
                writer.close();
                setSaved(true);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @SerializedJSONObjectValue(key = "field")
    public final HField getField() {
        return field;
    }

    /**
     * @return ObservableList<HPath>
     */
    @SerializedJSONObjectValue(key = "paths")
    public final ObservableList<HPath> getPaths() {
        return paths;
    }

    public final IntegerProperty selectedPathIndexProperty() {
        return selectedPathIndex;
    }

    public final void setSelectedPathIndex(int value) {
        selectedPathIndex.set(value);
    }

    public final int getSelectedPathIndex() {
        return selectedPathIndex.get();
    }

    public final HPath getSelectedPath() {
        return paths.get(getSelectedPathIndex());
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

    /**
     * @return BooleanProperty
     */
    public BooleanProperty savedProperty() {
        return savedProperty;
    }

    /**
     * @param value
     */
    public void setSaved(boolean value) {
        savedProperty.set(value);
    }

    /**
     * @return boolean
     */
    public boolean isSaved() {
        return savedProperty.get();
    }
}
