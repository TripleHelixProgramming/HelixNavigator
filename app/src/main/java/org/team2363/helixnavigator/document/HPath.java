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
    private final DoubleProperty zoomXOffset = new SimpleDoubleProperty(this, "zoomXOffset", 0.0);
    private final DoubleProperty zoomYOffset = new SimpleDoubleProperty(this, "zoomYOffset", 0.0);
    private final ReadOnlyBooleanWrapper inPolygonPointMode = new ReadOnlyBooleanWrapper(this, "inPolygonPointMode", false);

    @DeserializedJSONConstructor
    public HPath() {
        waypointsSelectionModel = new HSelectionModel<>(waypoints);
        obstaclesSelectionModel = new HSelectionModel<>(obstacles);
        obstaclesSelectionModel.getSelectedIndices().addListener((ListChangeListener.Change<? extends Integer> change) -> {
            if (waypointsSelectionModel.getSelectedIndices().isEmpty() && obstaclesSelectionModel.getSelectedIndices().size() == 1 && obstaclesSelectionModel.getSelectedItems().get(0).isPolygon()) {
                if (getPolygonPointsSelectionModel() != null) { // if not first time using it
                    getPolygonPointsSelectionModel().clear();
                }
                setPolygonPointsSelectionModel(new HSelectionModel<HPolygonPoint>(((HPolygonObstacle) obstaclesSelectionModel.getSelectedItems().get(0)).getPoints()));
                setInPolygonPointModeProperty(true);
            } else {
                setInPolygonPointModeProperty(false);
            }
        });
    }

    private double dragInitialX;
    private double dragInitialY;
    private double zoomOffsetInitialX;
    private double zoomOffsetInitialY;
    public void handleMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            dragInitialX = event.getX();
            dragInitialY = event.getY();
            zoomOffsetInitialX = getZoomXOffset();
            zoomOffsetInitialY = getZoomYOffset();
        }
    }
    public void handleMouseDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            setZoomXOffset(zoomOffsetInitialX + (event.getX() - dragInitialX));
            setZoomYOffset(zoomOffsetInitialY + (event.getY() - dragInitialY));
        }
    }

    public void handleElementsPressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            dragInitialX = event.getX();
            dragInitialY = event.getY();
        }
    }
    public void handleElementsDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
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
        setZoomXOffset(getZoomXOffset() - deltaX);
        setZoomYOffset(getZoomYOffset() - deltaY);
    }

    public void zoom(double factor, double pivotX, double pivotY) {
        // This code allows for zooming in or out about a certain point
        double s = factor;
        double xci = getZoomXOffset();
        double xp = pivotX;
        double xd = (1-s)*(xp-xci);
        double yci = getZoomYOffset();
        double yp = pivotY;
        double yd = (1-s)*(yp-yci);
        setZoomXOffset(getZoomXOffset() + xd);
        setZoomYOffset(getZoomYOffset() + yd);
        setZoomScale(getZoomScale() * s);
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

    public final void setZoomScale(double value) {
        zoomScale.set(value);
    }

    public final double getZoomScale() {
        return zoomScale.get();
    }

    public final DoubleProperty zoomXOffsetProperty() {
        return zoomXOffset;
    }

    public final void setZoomXOffset(double value) {
        zoomXOffset.set(value);
    }

    public final double getZoomXOffset() {
        return zoomXOffset.get();
    }

    public final DoubleProperty zoomYOffsetProperty() {
        return zoomYOffset;
    }

    public final void setZoomYOffset(double value) {
        zoomYOffset.set(value);
    }

    public final double getZoomYOffset() {
        return zoomYOffset.get();
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
