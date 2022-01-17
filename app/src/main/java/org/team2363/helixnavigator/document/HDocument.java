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
import org.team2363.helixnavigator.document.field.image.HReferenceFieldImage;
import org.team2363.helixnavigator.global.Standards;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * This class represents a document in Helix Navigator
 * 
 * @author Justin Babilino
 */
@JSONSerializable
public class HDocument {

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
    private final ObservableList<HPath> paths = FXCollections.<HPath>observableArrayList();
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
    private final ReadOnlyIntegerWrapper selectedPathIndex = new ReadOnlyIntegerWrapper(this, "selectedPathIndex", -1);
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

    /**
     * Constructs an {@code HDocument}.
     */
    @DeserializedJSONConstructor
    public HDocument() {
        paths.addListener((ListChangeListener.Change<? extends HPath> change) -> updateSelectedPathIndex());
        selectedPathIndex.addListener((currentIndex, oldIndex, newIndex) -> updateSelectedPath());
    }

    /**
     * Called when the path list is changed, this method controls the selected path index
     * and prevents it from being in an illegal state.
     */
    private void updateSelectedPathIndex() {
        if (!hasPaths()) {
            setSelectedPathIndex(-1);
        } else if (!isPathSelected()) {
            setSelectedPathIndex(0);    // if the selected path index is now out of bounds, 
                                        // and you can select a path, select one!
        }
        updateSelectedPath();
    }

    /**
     * Called when the selected path index changes, this method 
     * synchronizes the {@code selectedPathIndex} with the {@code selectedPath}.
     */
    private void updateSelectedPath() {
        if (isPathSelected()) {
            setSelectedPath(paths.get(getSelectedPathIndex()));
        } else {
            setSelectedPath(null);
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
    public final boolean hasPaths() {
        return !getPaths().isEmpty();
    }

    /**
     * <p>
     * Returns {@code true} if the selected path index is within the bounds of the path
     * list. Equivalent to:
     * </p>
     * <pre>
     * getSelectedPathIndex() >= 0 && getSelectedPathIndex() < getPaths().size()
     * </pre>
     * 
     * @return {@code true} if and only if the selected path index is within bounds
     */
    public final boolean isPathSelected() {
        return getSelectedPathIndex() >= 0 && getSelectedPathIndex() < getPaths().size();
    }

    /**
     * Returns {@code true} if a save location on the disk has been selected.
     * 
     * @return {@code getSaveLocation() != null}
     */
    public boolean isSaveLocationSet() {
        return getSaveLocation() != null;
    }

    private double lastBackgroundDragX;
    private double lastBackgroundDragY;
    public void handleBackgroundPressed(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) { // TODO: switch to switch statement
            lastBackgroundDragX = event.getX();
            lastBackgroundDragY = event.getY();
        }
    }
    public void handleBackgroundDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            pan(event.getX() - lastBackgroundDragX, event.getY() - lastBackgroundDragY);
            lastBackgroundDragX = event.getX();
            lastBackgroundDragY = event.getY();
        }
    }

    public void handleScroll(ScrollEvent event) {
        int pixels = (int) (-event.getDeltaY());
        double factor;
        if (pixels >= 0) {
            factor = 0.995;
        } else {
            factor = 1.005;
            pixels = -pixels;
        }
        double pivotX = event.getX();
        double pivotY = event.getY();
        for (int i = 0; i < pixels; i++) {
            zoom(factor, pivotX, pivotY);
        }
    }

    public void pan(double deltaX, double deltaY) {
        setZoomTranslateX(getZoomTranslateX() + deltaX);
        setZoomTranslateY(getZoomTranslateY() + deltaY);
    }

    public void zoom(double factor, double pivotX, double pivotY) {
        // This code allows for zooming in or out about a certain point
        double s = factor;
        double xci = getZoomTranslateX();
        double xp = pivotX;
        double xd = (1-s)*(xp-xci);
        double yci = getZoomTranslateY();
        double yp = pivotY;
        double yd = (1-s)*(yp-yci);
        setZoomTranslateX(getZoomTranslateX() + xd);
        setZoomTranslateY(getZoomTranslateY() + yd);
        setZoomScale(getZoomScale() * s);
    }

    private double lastElementsDragX;
    private double lastElementsDragY;
    public void handleElementsDragBegin(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            lastElementsDragX = event.getSceneX();
            lastElementsDragY  = event.getSceneY();
        }
    }
    public void handleElementsDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            double deltaX = event.getSceneX() - lastElementsDragX;
            double deltaY = event.getSceneY() - lastElementsDragY;
            double scaledDeltaX = deltaX / getZoomScale();
            double scaledDeltaY = -deltaY / getZoomScale();
            getSelectedPath().moveSelectedElementsRelative(scaledDeltaX, scaledDeltaY);
            lastElementsDragX = event.getSceneX();
            lastElementsDragY = event.getSceneY();
        }
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

    @DeserializedJSONTarget
    public final void setZoomScale(@DeserializedJSONObjectValue(key = "zoom_scale") double value) {
        zoomScale.set(value);
    }

    @SerializedJSONObjectValue(key = "zoom_scale")
    public final double getZoomScale() {
        return zoomScale.get();
    }

    public final DoubleProperty zoomTranslateXProperty() {
        return zoomTranslateX;
    }

    @DeserializedJSONTarget
    public final void setZoomTranslateX(@DeserializedJSONObjectValue(key = "zoom_translate_x") double value) {
        zoomTranslateX.set(value);
    }

    @SerializedJSONObjectValue(key = "zoom_translate_x")
    public final double getZoomTranslateX() {
        return zoomTranslateX.get();
    }

    public final DoubleProperty zoomTranslateYProperty() {
        return zoomTranslateY;
    }

    @DeserializedJSONTarget
    public final void setZoomTranslateY(@DeserializedJSONObjectValue(key = "zoom_translate_y") double value) {
        zoomTranslateY.set(value);
    }

    @SerializedJSONObjectValue(key = "zoom_translate_y")
    public final double getZoomTranslateY() {
        return zoomTranslateY.get();
    }

    @DeserializedJSONTarget
    public final void setPaths(@DeserializedJSONObjectValue(key = "paths") List<? extends HPath> newPaths) {
        paths.setAll(newPaths);
    }

    @SerializedJSONObjectValue(key = "paths")
    public final ObservableList<HPath> getPaths() {
        return paths;
    }

    public final ReadOnlyIntegerProperty selectedPathIndexProperty() {
        return selectedPathIndex.getReadOnlyProperty();
    }

    @DeserializedJSONTarget
    public final void setSelectedPathIndex(@DeserializedJSONObjectValue(key = "selected_path_index") int value) {
        if (value < 0 && hasPaths()) {
            System.out.println("WARNING: illegal selected path index was attempted: " + value);
            value = 0;
        } else if (value >= paths.size()) {
            System.out.println("WARNING: illegal selected path index was attempted: " + value);
            value = paths.size() - 1;
        }
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

    public final ObjectProperty<File> saveLocationProperty() {
        return saveLocation;
    }

    public final void setSaveLocation(File value) {
        saveLocation.set(value);
    }

    public final File getSaveLocation() {
        return saveLocation.get();
    }

    public final BooleanProperty savedProperty() {
        return savedProperty;
    }

    public final void setSaved(boolean value) {
        savedProperty.set(value);
    }

    public final boolean isSaved() {
        return savedProperty.get();
    }
}