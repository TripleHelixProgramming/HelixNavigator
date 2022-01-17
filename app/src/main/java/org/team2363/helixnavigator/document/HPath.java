package org.team2363.helixnavigator.document;

import java.util.List;

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

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

@JSONSerializable
public class HPath {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ObservableList<HWaypoint> waypoints = FXCollections.<HWaypoint>observableArrayList();
    private final HSelectionModel<HWaypoint> waypointsSelectionModel;
    private final ObservableList<HObstacle> obstacles = FXCollections.<HObstacle>observableArrayList();
    private final HSelectionModel<HObstacle> obstaclesSelectionModel;
    private final ReadOnlyBooleanWrapper inPolygonPointMode = new ReadOnlyBooleanWrapper(this, "inPolygonPointMode", false);
    private final ReadOnlyObjectWrapper<HSelectionModel<HPolygonPoint>> polygonPointsSelectionModel = new ReadOnlyObjectWrapper<>(this, "polygonPointsSelectionModel", null);

    @DeserializedJSONConstructor
    public HPath() {
        waypointsSelectionModel = new HSelectionModel<>(waypoints);
        obstaclesSelectionModel = new HSelectionModel<>(obstacles);
        waypointsSelectionModel.getSelectedIndices().addListener((ListChangeListener.Change<? extends Integer> change) -> {
            updateInPolygonPointMode();
            updatePolygonPointsSelectionModel();
        });
        obstaclesSelectionModel.getSelectedIndices().addListener((ListChangeListener.Change<? extends Integer> change) -> {
            updateInPolygonPointMode();
            updatePolygonPointsSelectionModel();
        });
    }

    private void updateInPolygonPointMode() {
        setInPolygonPointMode(
                waypointsSelectionModel.getSelectedIndices().isEmpty()
                && obstaclesSelectionModel.getSelectedIndices().size() == 1
                && obstaclesSelectionModel.getSelectedItems().get(0).isPolygon());
    }
    private void updatePolygonPointsSelectionModel() {
        if (getInPolygonPointMode()) {
            setPolygonPointsSelectionModel(new HSelectionModel<HPolygonPoint>(((HPolygonObstacle) obstacles.get(0)).getPoints()));
        } else {
            setPolygonPointsSelectionModel(null);
        }
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

    public final ReadOnlyBooleanProperty inPolygonPointModeProperty() {
        return inPolygonPointMode.getReadOnlyProperty();
    }

    private final void setInPolygonPointMode(boolean value) {
        inPolygonPointMode.set(value);
    }

    public final boolean getInPolygonPointMode() {
        return inPolygonPointMode.get();
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
    
    @Override
    public String toString() {
        return JSONSerializer.serializeString(this);
    }
}