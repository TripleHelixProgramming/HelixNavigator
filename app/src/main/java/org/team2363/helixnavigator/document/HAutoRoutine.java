package org.team2363.helixnavigator.document;

import java.util.List;

import org.team2363.helixnavigator.document.compiled.HCompiledAutoRoutine;
import org.team2363.helixnavigator.document.drivetrain.HDrivetrain;
import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.document.timeline.HTimelineElement;
import org.team2363.helixtrajectory.Path;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.InvalidJSONTranslationConfiguration;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.JSONSerializer;
import com.jlbabilino.json.JSONSerializerException;
import com.jlbabilino.json.SerializedJSONObjectValue;

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
public class HAutoRoutine implements HNamedElement {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ObservableList<HTimelineElement> timeline = FXCollections.observableArrayList();
    private final HSelectionModel<HTimelineElement> timelineSelectionModel = new HSelectionModel<>(timeline);
    private final ObservableList<HObstacle> obstacles = FXCollections.observableArrayList();
    private final HSelectionModel<HObstacle> obstaclesSelectionModel = new HSelectionModel<>(obstacles);
    private final ReadOnlyBooleanWrapper inPolygonPointMode = new ReadOnlyBooleanWrapper(this, "inPolygonPointMode", false);
    private final ReadOnlyObjectWrapper<HSelectionModel<HPolygonPoint>> polygonPointsSelectionModel = new ReadOnlyObjectWrapper<>(this, "polygonPointsSelectionModel", null);
    private final ReadOnlyObjectWrapper<HCompiledAutoRoutine> compiledAutoRoutine = new ReadOnlyObjectWrapper<>(this, "compiledAutoRoutine", null);

    @DeserializedJSONConstructor
    public HAutoRoutine() {
        timelineSelectionModel.getSelectedItems().addListener((ListChangeListener.Change<? extends HTimelineElement> change) -> {
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
                timelineSelectionModel.getSelectedIndices().isEmpty()
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
        getTimelineSelectionModel().getSelectedItems().forEach(element -> element.transformRelative(transform));
        getObstaclesSelectionModel().getSelectedItems().forEach(element -> element.transformRelative(transform));
    }

    public void moveSelectedElementsRelative(double deltaX, double deltaY) {
        getTimelineSelectionModel().getSelectedItems().forEach(element -> element.translateRelative(deltaX, deltaY));
        getObstaclesSelectionModel().getSelectedItems().forEach(element -> element.translateRelative(deltaX, deltaY));
    }

    public void moveSelectedElementsRelative(double deltaX, double deltaY, HPathElement excludedElement) {
        getTimelineSelectionModel().getSelectedItems().forEach(element -> {
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

    public void clearTimelineSelection() {
        timelineSelectionModel.clearSelection();
    }
    public void clearObstaclesSelection() {
        obstaclesSelectionModel.clearSelection();
    }
    public void clearSelection() {
        clearTimelineSelection();
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
    public final void setTimeline(@DeserializedJSONObjectValue(key = "timeline") List<? extends HTimelineElement> newTimeline) {
        timeline.setAll(newTimeline);
    }

    @SerializedJSONObjectValue(key = "timeline")
    public final ObservableList<HTimelineElement> getTimeline() {
        return timeline;
    }

    public final HSelectionModel<HTimelineElement> getTimelineSelectionModel() {
        return timelineSelectionModel;
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

    public final ReadOnlyObjectProperty<HCompiledAutoRoutine> compiledAutoRoutineProperty() {
        return compiledAutoRoutine.getReadOnlyProperty();
    }
    private final void setCompiledAutoRoutine(HCompiledAutoRoutine value) {
        compiledAutoRoutine.set(value);
    }
    public final HCompiledAutoRoutine getCompiledAutoRoutine() {
        return compiledAutoRoutine.get();
    }

    public boolean compileAutoRoutine(HDrivetrain drivetrain) {
        
        return false;
    }

    private 

    public static HAutoRoutine defaultPath() {
        HAutoRoutine path = new HAutoRoutine();
        return path;
    }

    private Path toPath(int firstWaypointIndex, ) {
        
        List<Waypoint> htWaypoints = new ArrayList<>();
        int i = firstWaypointIndex;
        while (timelineElements.get(i).isInitialGuess()) {
            i++;
        }
        while (i < timelineElements.size()) {
            int waypointIndex = i;
            i++;
            List<InitialGuessPoint> initialGuessPoints = new ArrayList<>();
            while (i < timelineElements.size() && timelineElements.get(i).isInitialGuess()) {
                initialGuessPoints.add(((HInitialGuessPoint) timelineElements.get(i)).toInitialGuessPoint());
                i++;
            }
            InitialGuessPoint[] initialGuessPointsArray = initialGuessPoints.toArray(new InitialGuessPoint[0]);
            switch (timelineElements.get(waypointIndex).getWaypointType()) {
                case SOFT:
                    htWaypoints.add(((HSoftWaypoint) timelineElements.get(waypointIndex)).toWaypoint(initialGuessPointsArray));
                    break;
                case HARD:
                    htWaypoints.add(((HHardWaypoint) timelineElements.get(waypointIndex)).toWaypoint(initialGuessPointsArray));
                    break;
                case CUSTOM:
                    htWaypoints.add(((HWaypoint) timelineElements.get(waypointIndex)).toWaypoint(initialGuessPointsArray));
                    break;
                default:
                    break;
            }
        }
        return new Path(htWaypoints.toArray(new Waypoint[0]));
        return null; //TODO: fix this
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