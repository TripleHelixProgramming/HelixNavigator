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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.field.image.HReferenceFieldImage;
import org.team2363.helixnavigator.global.Standards;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * This class represents a document in Helix Navigator
 * 
 * @author Justin Babilino
 */
@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HDocument {

    private static final Logger LOGGER = Logger.getLogger("org.team2363.helixnavigator.document");

    /**
     * The field that contains the image that the editor pane will display behind
     * the path editor.
     */
    private final ObjectProperty<HFieldImage> fieldImage = new SimpleObjectProperty<>(this, "fieldImage", new HReferenceFieldImage(Standards.DEFAULT_FIELD_IMAGE));
    /**
     * The factor the path viewer is scaled by
     */
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
    /**
     * The horizontal offset of the path viewer
     */
    private final DoubleProperty zoomTranslateX = new SimpleDoubleProperty(this, "zoomTranslateX", 0.0);
    /**
     * The vertical offset of the path viewer
     */
    private final DoubleProperty zoomTranslateY = new SimpleDoubleProperty(this, "zoomTranslateY", 0.0);
    /**
     * The list of paths in this document
     */
    private final ObservableList<HAutoRoutine> autoRoutines = FXCollections.<HAutoRoutine>observableArrayList();
    /**
     * <p>
     * The currently selected path index. It will equal {@code -1} if and only if
     * there are no paths in the document. Otherwise, an index within the bounds
     * of the path list will be set.
     * </p>
     * <p>
     * This is read only so that it cannot be put in illegal states that don't comply
     * with the above conditions.
     * </p>
     */
    private final ReadOnlyIntegerWrapper selectedAutoRoutineIndex = new ReadOnlyIntegerWrapper(this, "selectedAutoRoutineIndex", -1);
    /**
     * <p>
     * The currently selected path that aligns with the currently selected index.
     * {@code null} is similar to {@code -1} for {@code selectedPathIndex}, meaning that
     * {@code selectedPath} is {@code null} if and only if there are no paths in the document.
     * If {@code selectedPathIndex} is set to an index greater than or equal to zero,
     * {@code selectedPath} will be {@code paths.get(getSelectedPathIndex())}
     * </p>
     * <p>
     * This is read only so that it cannot be put in illegal states that don't comply
     * with the above conditions.
     */
    private final ReadOnlyObjectWrapper<HAutoRoutine> selectedAutoRoutine = new ReadOnlyObjectWrapper<>(this, "selectedAutoRoutine", null);
    /**
     * The robot configuration for this document
     */
    private final HRobotConfiguration robotConfiguration;
    /**
     * The default units to use in text input boxes
     */
    private final HUnitPreferences unitPreferences;
    /**
     * The file path that this document should be saved to
     */
    private final ObjectProperty<File> saveLocation = new SimpleObjectProperty<>(this, "saveLocation", null);
    /**
     * the property representing whether or not this document has been saved in its
     * entirety to a file
     */
    private final ReadOnlyBooleanWrapper savedProperty = new ReadOnlyBooleanWrapper(this, "saved", false); // change to true later when state management is added

    /**
     * Constructs a blank {@code HDocument}.
     */
    public HDocument() {
        this(Collections.emptyMap(), new HRobotConfiguration(), new HUnitPreferences());
    }

    @DeserializedJSONConstructor
    public HDocument(
            @DeserializedJSONObjectValue(key = "auto_routines") Map<String, HAutoRoutine> initialAutoRoutinesMap,
            @DeserializedJSONObjectValue(key = "robot_configuration") HRobotConfiguration robotConfiguration,
            @DeserializedJSONObjectValue(key = "unit_preferences") HUnitPreferences unitPreferences) {
        autoRoutines.addListener((ListChangeListener.Change<? extends HAutoRoutine> change) -> updateSelectedAutoRoutineIndex());
        selectedAutoRoutineIndex.addListener((currentIndex, oldIndex, newIndex) -> updateSelectedAutoRoutine());
        this.robotConfiguration = robotConfiguration;
        this.unitPreferences = unitPreferences;

        initialAutoRoutinesMap.entrySet().stream().sorted((a, b) -> a.getKey().compareTo(b.getKey())).forEach(entry -> {
            String autoRoutineName = entry.getKey();
            HAutoRoutine autoRoutine = entry.getValue();
            autoRoutine.setName(autoRoutineName);
            autoRoutines.add(autoRoutine);
        }); 
    }

    /**
     * Called when the path list is changed, this method controls the selected path index
     * and prevents it from being in an illegal state.
     */
    private void updateSelectedAutoRoutineIndex() {
        if (!hasAutoRoutines()) {
            setSelectedAutoRoutineIndex(-1);
        } else if (!isAutoRoutineSelected()) {
            setSelectedAutoRoutineIndex(0);    // if the selected path index is now out of bounds, 
                                        // and you can select a path, select one!
        } else {
            updateSelectedAutoRoutine();
        }
    }

    /**
     * Called when the selected path index changes, this method 
     * synchronizes the {@code selectedPathIndex} with the {@code selectedPath}.
     */
    private void updateSelectedAutoRoutine() {
        if (isAutoRoutineSelected()) {
            setSelectedAutoRoutine(autoRoutines.get(getSelectedAutoRoutineIndex()));
        } else {
            setSelectedAutoRoutine(null);
        }
    }

    /**
     * <p>
     * Returns {@code true} if this document has any paths. Equivalent to:
     * </p>
     * <pre>
     * !getPaths().isEmpty()
     * </pre>
     * 
     * @return {@code true} if and only if the path list is not empty
     */
    public final boolean hasAutoRoutines() {
        return !getAutoRoutines().isEmpty();
    }

    /**
     * <p>
     * Returns {@code true} if the selected path index is within the bounds of the path
     * list. Equivalent to:
     * </p>
     * <pre>
     * getSelectedPathIndex() >= 0 && getSelectedPathIndex() < getPaths().size()
     * </pre>
     * <p>
     * This method should always return the same value as hasPaths(). The only
     * reason this method is public is because I may decide later that allowing
     * the user to have no path open while there are paths availible is okay.
     * 
     * @return {@code true} if and only if the selected path index is within bounds
     */
    public final boolean isAutoRoutineSelected() {
        return getSelectedAutoRoutineIndex() >= 0 && getSelectedAutoRoutineIndex() < getAutoRoutines().size();
    }

    /**
     * Returns {@code true} if a save location on the disk has been selected.
     * 
     * @return {@code getSaveLocation() != null}
     */
    public boolean isSaveLocationSet() {
        return getSaveLocation() != null;
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

    public final DoubleProperty zoomScaleProperty() {
        return zoomScale;
    }
    // @DeserializedJSONTarget
    public final void setZoomScale(@DeserializedJSONObjectValue(key = "zoom_scale") double value) {
        zoomScale.set(value);
    }
    // @SerializedJSONObjectValue(key = "zoom_scale")
    public final double getZoomScale() {
        return zoomScale.get();
    }

    public final DoubleProperty zoomTranslateXProperty() {
        return zoomTranslateX;
    }
    // @DeserializedJSONTarget
    public final void setZoomTranslateX(@DeserializedJSONObjectValue(key = "zoom_translate_x") double value) {
        zoomTranslateX.set(value);
    }
    // @SerializedJSONObjectValue(key = "zoom_translate_x")
    public final double getZoomTranslateX() {
        return zoomTranslateX.get();
    }

    public final DoubleProperty zoomTranslateYProperty() {
        return zoomTranslateY;
    }
    // @DeserializedJSONTarget
    public final void setZoomTranslateY(@DeserializedJSONObjectValue(key = "zoom_translate_y") double value) {
        zoomTranslateY.set(value);
    }
    // @SerializedJSONObjectValue(key = "zoom_translate_y")
    public final double getZoomTranslateY() {
        return zoomTranslateY.get();
    }

    // @SerializedJSONObjectValue(key = "auto_routines")
    public final ObservableList<HAutoRoutine> getAutoRoutines() {
        return autoRoutines;
    }
    @SerializedJSONObjectValue(key = "auto_routines")
    public final Map<String, HAutoRoutine> getAutoRoutinesMap() {
        Map<String, HAutoRoutine> map = new HashMap<>(getAutoRoutines().size());
        for (HAutoRoutine autoRoutine : getAutoRoutines()) {
            map.put(autoRoutine.getName(), autoRoutine);
        }
        return map;
    }

    public final ReadOnlyIntegerProperty selectedAutoRoutineIndexProperty() {
        return selectedAutoRoutineIndex.getReadOnlyProperty();
    }
    // @DeserializedJSONTarget
    public final void setSelectedAutoRoutineIndex(@DeserializedJSONObjectValue(key = "selected_auto_routine_index") int value) {
        if (value < 0 && hasAutoRoutines()) {
            LOGGER.warning("WARNING: illegal selected path index was attempted: " + value);
            value = 0;
        } else if (value >= autoRoutines.size()) {
            LOGGER.warning("WARNING: illegal selected path index was attempted: " + value);
            value = autoRoutines.size() - 1;
        }
        selectedAutoRoutineIndex.set(value);
    }
    // @SerializedJSONObjectValue(key = "selected_path_index")
    public final int getSelectedAutoRoutineIndex() {
        return selectedAutoRoutineIndex.get();
    }

    public final ReadOnlyObjectProperty<HAutoRoutine> selectedAutoRoutineProperty() {
        return selectedAutoRoutine.getReadOnlyProperty();
    }
    private final void setSelectedAutoRoutine(HAutoRoutine value) {
        selectedAutoRoutine.set(value);
    }
    public final HAutoRoutine getSelectedAutoRoutine() {
        return selectedAutoRoutine.get();
    }

    @SerializedJSONObjectValue(key = "robot_configuration")
    public final HRobotConfiguration getRobotConfiguration() {
        return robotConfiguration;
    }

    @SerializedJSONObjectValue(key = "unit_preferences")
    public final HUnitPreferences getUnitPreferences() {
        return unitPreferences;
    }

    public final ObjectProperty<File> saveLocationProperty() {
        return saveLocation;
    }
    public final void setSaveLocation(File value) {
        saveLocation.set(value);
    }
    public final File getSaveLocation() {
        return saveLocation.get();
    }

    public final ReadOnlyBooleanProperty savedProperty() {
        return savedProperty.getReadOnlyProperty();
    }
    final void setSaved(boolean value) {
        savedProperty.set(value);
    }
    public final boolean isSaved() {
        return savedProperty.get();
    }

    public static HDocument defaultDocument() {
        HDocument document = new HDocument();
        document.getAutoRoutines().add(HAutoRoutine.defaultPath());
        return document;
    }
}