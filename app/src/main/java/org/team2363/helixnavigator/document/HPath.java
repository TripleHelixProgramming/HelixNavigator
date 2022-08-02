package org.team2363.helixnavigator.document;

import java.util.ArrayList;
import java.util.List;

import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.document.waypoint.HCustomWaypoint;
import org.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import org.team2363.helixnavigator.document.waypoint.HInitialGuessWaypoint;
import org.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import org.team2363.helixnavigator.document.waypoint.HWaypoint;
import org.team2363.helixtrajectory.InitialGuessPoint;
import org.team2363.helixtrajectory.Path;
import org.team2363.helixtrajectory.Waypoint;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.JSONSerializerException;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.transform.Transform;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HPath {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ObservableList<HWaypoint> waypoints = FXCollections.<HWaypoint>observableArrayList();
    private final HSelectionModel<HWaypoint> waypointsSelectionModel;
    private final ObservableList<HObstacle> obstacles = FXCollections.<HObstacle>observableArrayList();
    private final HSelectionModel<HObstacle> obstaclesSelectionModel;
    private final ReadOnlyBooleanWrapper inPolygonPointMode = new ReadOnlyBooleanWrapper(this, "inPolygonPointMode", false);
    private final ReadOnlyObjectWrapper<HSelectionModel<HPolygonPoint>> polygonPointsSelectionModel = new ReadOnlyObjectWrapper<>(this, "polygonPointsSelectionModel", null);
    private final ReadOnlyObjectWrapper<HTrajectory> trajectory = new ReadOnlyObjectWrapper<HTrajectory>(this, "trajectory", null);

    @DeserializedJSONConstructor
    public HPath() {
        waypointsSelectionModel = new HSelectionModel<>(waypoints);
        obstaclesSelectionModel = new HSelectionModel<>(obstacles);
        waypointsSelectionModel.getSelectedItems().addListener((ListChangeListener.Change<? extends HWaypoint> change) -> {
            updateInPolygonPointMode();
            updatePolygonPointsSelectionModel();
        });
        obstaclesSelectionModel.getSelectedItems().addListener((ListChangeListener.Change<? extends HObstacle> change) -> {
            updateInPolygonPointMode();
            updatePolygonPointsSelectionModel();
        });
    }

    private void updateInPolygonPointMode() {
        setInPolygonPointMode(
                waypointsSelectionModel.getSelectedIndices().isEmpty()
                && obstaclesSelectionModel.getSelectedItems().size() == 1
                && obstaclesSelectionModel.getSelectedItems().get(0).isPolygon());
    }
    private void updatePolygonPointsSelectionModel() {
        if (getInPolygonPointMode()) {
            setPolygonPointsSelectionModel(new HSelectionModel<HPolygonPoint>(((HPolygonObstacle) obstaclesSelectionModel.getSelectedItems().get(0)).getPoints()));
        } else {
            setPolygonPointsSelectionModel(null);
        }
    }

    public void transformSelectedElementsRelative(Transform transform) {
        getWaypointsSelectionModel().getSelectedItems().forEach(element -> element.transformRelative(transform));
        getObstaclesSelectionModel().getSelectedItems().forEach(element -> element.transformRelative(transform));
    }

    public void moveSelectedElementsRelative(double deltaX, double deltaY) {
        getWaypointsSelectionModel().getSelectedItems().forEach(element -> element.translateRelative(deltaX, deltaY));
        getObstaclesSelectionModel().getSelectedItems().forEach(element -> element.translateRelative(deltaX, deltaY));
    }

    public void moveSelectedElementsRelative(double deltaX, double deltaY, HPathElement excludedElement) {
        getWaypointsSelectionModel().getSelectedItems().forEach(element -> {
            if (element != excludedElement) {
                element.translateRelative(deltaX, deltaY);
            }
        });
        getObstaclesSelectionModel().getSelectedItems().forEach(element -> {
            if (element != excludedElement) {
                element.translateRelative(deltaX, deltaY);
            }
        });
    }

    public void moveSelectedPolygonPointsRelative(double deltaX, double deltaY, HPolygonPoint excludedPolygonPoint) {
        getPolygonPointsSelectionModel().getSelectedItems().forEach(element -> {
            if (element != excludedPolygonPoint) {
                element.translateRelative(deltaX, deltaY);
            }
        });
    }

    public void clearWaypointsSelection() {
        waypointsSelectionModel.clearSelection();
    }
    public void clearObstaclesSelection() {
        obstaclesSelectionModel.clearSelection();
    }
    public void clearSelection() {
        clearWaypointsSelection();
        clearObstaclesSelection();
    }

    public void clearPolygonPointSelection() {
        if (getInPolygonPointMode()) {
            getPolygonPointsSelectionModel().clearSelection();
        }
    }

    public final StringProperty nameProperty() {
        return name;
    }

    // @DeserializedJSONTarget
    public final void setName(@DeserializedJSONObjectValue(key = "name") String value) {
        name.set(value);
    }

    // @SerializedJSONObjectValue(key = "name")
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

    public final ReadOnlyObjectProperty<HTrajectory> trajectoryProperty() {
        return trajectory.getReadOnlyProperty();
    }

    // TODO: Make this private eventually
    public final void setTrajectory(HTrajectory value) {
        trajectory.set(value);
    }

    public final HTrajectory getTrajectory() {
        return trajectory.get();
    }

    public Path toPath() {
        List<Waypoint> htWaypoints = new ArrayList<>();
        int i = 0;
        while (waypoints.get(i).isInitialGuess()) {
            i++;
        }
        while (i < waypoints.size()) {
            int waypointIndex = i;
            i++;
            List<InitialGuessPoint> initialGuessPoints = new ArrayList<>();
            while (i < waypoints.size() && waypoints.get(i).isInitialGuess()) {
                initialGuessPoints.add(((HInitialGuessWaypoint) waypoints.get(i)).toInitialGuessPoint());
                i++;
            }
            InitialGuessPoint[] initialGuessPointsArray = initialGuessPoints.toArray(new InitialGuessPoint[0]);
            switch (waypoints.get(waypointIndex).getWaypointType()) {
                case SOFT:
                    htWaypoints.add(((HSoftWaypoint) waypoints.get(waypointIndex)).toWaypoint(initialGuessPointsArray));
                    break;
                case HARD:
                    htWaypoints.add(((HHardWaypoint) waypoints.get(waypointIndex)).toWaypoint(initialGuessPointsArray));
                    break;
                case CUSTOM:
                    htWaypoints.add(((HCustomWaypoint) waypoints.get(waypointIndex)).toWaypoint(initialGuessPointsArray));
                    break;
                default:
                    break;
            }
        }
        return new Path(htWaypoints.toArray(new Waypoint[0]));
    }

    @Override
    public String toString() {
        try {
            return JSONSerializer.serializeString(this);
        } catch (InvalidJSONTranslationConfiguration | JSONSerializerException e) {
            return "";
        }
    }
}