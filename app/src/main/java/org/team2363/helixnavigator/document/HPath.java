package org.team2363.helixnavigator.document;

import java.util.List;
import java.util.regex.Pattern;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.SerializedJSONObjectValue;

import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;

@JSONSerializable
public class HPath {

    /**
     * One or more characters; a-z, 0-9, space, hyphen, underscore allowed
     */
    public static final Pattern VALID_PATH_NAME = Pattern.compile("[a-z0-9 _\\-]+", Pattern.CASE_INSENSITIVE);
    public static final int MAX_PATH_NAME_LENGTH = 50;

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ObservableList<HWaypoint> waypoints = FXCollections.<HWaypoint>observableArrayList();
    private final HSelectionModel<HWaypoint> waypointsSelectionModel;
    private final ObservableList<HObstacle> obstacles = FXCollections.<HObstacle>observableArrayList();
    private final HSelectionModel<HObstacle> obstaclesSelectionModel;
    private final ReadOnlyObjectWrapper<HSelectionModel<HPolygonPoint>> polygonPointsSelectionModel = new ReadOnlyObjectWrapper<>(this, "polygonPointsSelectionModel", null);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
    private final DoubleProperty zoomOffsetX = new SimpleDoubleProperty(this, "zoomOffsetX", 0.0);
    private final DoubleProperty zoomOffsetY = new SimpleDoubleProperty(this, "zoomOffsetY", 0.0);
    private final ReadOnlyBooleanWrapper inPolygonPointMode = new ReadOnlyBooleanWrapper(this, "inPolygonPointMode", false);

    @DeserializedJSONConstructor
    public HPath() {
        waypointsSelectionModel = new HSelectionModel<>(waypoints);
        obstaclesSelectionModel = new HSelectionModel<>(obstacles);
        // obstaclesSelectionModel.getSelectedIndices().addListener((ListChangeListener.Change<? extends Integer> change) -> {
        //     if (waypointsSelectionModel.getSelectedIndices().isEmpty() && obstaclesSelectionModel.getSelectedIndices().size() == 1 && obstaclesSelectionModel.getSelectedItems().get(0).isPolygon()) {
        //         if (getPolygonPointsSelectionModel() != null) { // if not first time using it
        //             getPolygonPointsSelectionModel().clear();
        //         }
        //         setPolygonPointsSelectionModel(new HSelectionModel<HPolygonPoint>(((HPolygonObstacle) obstaclesSelectionModel.getSelectedItems().get(0)).getPoints()));
        //         setInPolygonPointModeProperty(true);
        //     } else {
        //         setInPolygonPointModeProperty(false);
        //     }
        // });
    }

    private double backgroundDragInitialX;
    private double backgroundDragInitialY;
    private double zoomOffsetInitialX;
    private double zoomOffsetInitialY;
    public void handleBackgroundPressed(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) { // TODO: switch to switch statement
            backgroundDragInitialX = event.getX();
            backgroundDragInitialY = event.getY();
            zoomOffsetInitialX = getZoomOffsetX();
            zoomOffsetInitialY = getZoomOffsetY();
        } else if (event.getButton() == MouseButton.PRIMARY) {
            // clearSelection();
        }
    }
    public void handleBackgroundDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            setZoomOffsetX(zoomOffsetInitialX + (event.getX() - backgroundDragInitialX));
            setZoomOffsetY(zoomOffsetInitialY + (event.getY() - backgroundDragInitialY));
        }
    }

    private double lastElementsDragX;
    private double lastElementsDragY;
    public void handleElementsPressed(MouseEvent event) {
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
            moveSelectedElementsRelative(scaledDeltaX, scaledDeltaY);
            lastElementsDragX = event.getSceneX();
            lastElementsDragY = event.getSceneY();
        }
    }

    public void handleScroll(ScrollEvent event) {
        int pixels = (int) (-event.getDeltaY());
        System.out.println(pixels);
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

    public void handleZoom(ZoomEvent event) {
        zoom(scaleFactor(event.getTotalZoomFactor()), event.getX(), event.getY());
    }

    private double scaleFactor(double input) {
        return 1 + .05 * (input - 1);
    }

    public void pan(double deltaX, double deltaY) {
        setZoomOffsetX(getZoomOffsetX() - deltaX);
        setZoomOffsetY(getZoomOffsetY() - deltaY);
    }

    public void zoom(double factor, double pivotX, double pivotY) {
        // This code allows for zooming in or out about a certain point
        double s = factor;
        double xci = getZoomOffsetX();
        double xp = pivotX;
        double xd = (1-s)*(xp-xci);
        double yci = getZoomOffsetY();
        double yp = pivotY;
        double yd = (1-s)*(yp-yci);
        setZoomOffsetX(getZoomOffsetX() + xd);
        setZoomOffsetY(getZoomOffsetY() + yd);
        setZoomScale(getZoomScale() * s);
    }

    public void moveSelectedElementsRelative(double deltaX, double deltaY) {
        getWaypointsSelectionModel().getSelectedItems().forEach(element -> element.translateRelative(deltaX, deltaY));
    }

    public void clearSelection() {
        waypointsSelectionModel.clear();
        obstaclesSelectionModel.clear();
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

    @DeserializedJSONTarget
    public final void setWaypoints(@DeserializedJSONObjectValue(key = "waypoints") List<? extends HWaypoint> newWaypoints) {
        waypoints.setAll(newWaypoints);
    }

    @SerializedJSONObjectValue(key = "waypoints")
    public final ObservableList<HWaypoint> getWaypoints() {
        return waypoints;
    }

    public final HSelectionModel<HWaypoint> getWaypointsSelectionModel() {
        return waypointsSelectionModel;
    }

    @DeserializedJSONTarget
    public final void setObstacles(@DeserializedJSONObjectValue(key = "obstacles") List<? extends HObstacle> newObstacles) {
        obstacles.setAll(newObstacles);
    }

    @SerializedJSONObjectValue(key = "obstacles")
    public final ObservableList<HObstacle> getObstacles() {
        return obstacles;
    }

    public final HSelectionModel<HObstacle> getObstaclesSelectionModel() {
        return obstaclesSelectionModel;
    }

    public final ReadOnlyObjectProperty<HSelectionModel<HPolygonPoint>> polygonPointsSelectionModelProperty() {
        return polygonPointsSelectionModel.getReadOnlyProperty();
    }

    private final void setPolygonPointsSelectionModel(HSelectionModel<HPolygonPoint> value) {
        polygonPointsSelectionModel.set(value);
    }

    public final HSelectionModel<HPolygonPoint> getPolygonPointsSelectionModel() {
        return polygonPointsSelectionModel.get();
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

    public final DoubleProperty zoomOffsetXProperty() {
        return zoomOffsetX;
    }

    @DeserializedJSONTarget
    public final void setZoomOffsetX(@DeserializedJSONObjectValue(key = "zoom_offset_x") double value) {
        zoomOffsetX.set(value);
    }

    @SerializedJSONObjectValue(key = "zoom_offset_x")
    public final double getZoomOffsetX() {
        return zoomOffsetX.get();
    }

    public final DoubleProperty zoomOffsetYProperty() {
        return zoomOffsetY;
    }

    @DeserializedJSONTarget
    public final void setZoomOffsetY(@DeserializedJSONObjectValue(key = "zoom_offset_y") double value) {
        zoomOffsetY.set(value);
    }

    @SerializedJSONObjectValue(key = "zoom_offset_y")
    public final double getZoomOffsetY() {
        return zoomOffsetY.get();
    }

    public final ReadOnlyBooleanProperty inPolygonPointModeProperty() {
        return inPolygonPointMode.getReadOnlyProperty();
    }

    private final void setInPolygonPointModeProperty(boolean value) {
        inPolygonPointMode.set(value);
    }

    public final boolean getInPolygonPointMode() {
        return inPolygonPointMode.get();
    }
    
    @Override
    public String toString() {
        return JSONSerializer.serializeString(this);
    }
}
