package org.team2363.helixnavigator.document;

import java.util.ArrayList;
import java.util.List;

import org.team2363.helixnavigator.document.obstacle.HObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import org.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import org.team2363.helixnavigator.document.timeline.HInitialGuessPoint;
import org.team2363.helixnavigator.document.timeline.HTimelineElement;
import org.team2363.helixnavigator.document.timeline.HWaypoint;
import org.team2363.helixtrajectory.HolonomicPath;
import org.team2363.helixtrajectory.HolonomicWaypoint;
import org.team2363.helixtrajectory.InitialGuessPoint;
import org.team2363.helixtrajectory.Obstacle;

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
public class HPath {

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ObservableList<HTimelineElement> timeline = FXCollections.observableArrayList();
    private final HSelectionModel<HTimelineElement> timelineSelectionModel;
    private final ReadOnlyBooleanWrapper inPolygonPointMode = new ReadOnlyBooleanWrapper(this, "inPolygonPointMode", false);
    private final ReadOnlyObjectWrapper<HSelectionModel<HPolygonPoint>> polygonPointsSelectionModel = new ReadOnlyObjectWrapper<>(this, "polygonPointsSelectionModel", null);
    private final ReadOnlyObjectWrapper<HTrajectory> trajectory = new ReadOnlyObjectWrapper<HTrajectory>(this, "trajectory", null);

    @DeserializedJSONConstructor
    public HPath() {
        timelineSelectionModel = new HSelectionModel<>(timeline);
        timelineSelectionModel.getSelectedItems().addListener((ListChangeListener.Change<? extends HTimelineElement> change) -> {
            updateInPolygonPointMode();
            updatePolygonPointsSelectionModel();
        });
    }

    private void updateInPolygonPointMode() {
        boolean isPolygonSelected = false;
        for (HTimelineElement element : timelineSelectionModel.getSelectedItems()) {
            if (element.isObstacle() && ((HObstacle) element).isPolygon()) {
                isPolygonSelected = true;
            }
        }
        setInPolygonPointMode(
                timelineSelectionModel.getSelectedIndices().size() == 1
                && isPolygonSelected);
    }

    private void updatePolygonPointsSelectionModel() {
        if (getInPolygonPointMode()) {
            setPolygonPointsSelectionModel(new HSelectionModel<HPolygonPoint>(((HPolygonObstacle) timelineSelectionModel.getSelectedItems().get(0)).getPoints()));
        } else {
            setPolygonPointsSelectionModel(null);
        }
    }

    public void transformSelectedElementsRelative(Transform transform) {
        getTimelineSelectionModel().getSelectedItems().forEach(element -> element.transformRelative(transform));
    }

    public void moveSelectedElementsRelative(double deltaX, double deltaY) {
        getTimelineSelectionModel().getSelectedItems().forEach(element -> element.translateRelative(deltaX, deltaY));
    }

    public void moveSelectedElementsRelative(double deltaX, double deltaY, HPathElement excludedElement) {
        getTimelineSelectionModel().getSelectedItems().forEach(element -> {
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
    public final void setTimeline(@DeserializedJSONObjectValue(key = "timeline") List<? extends HWaypoint> newTimeline) {
        timeline.setAll(newTimeline);
    }
    @SerializedJSONObjectValue(key = "timeline")
    public final ObservableList<HTimelineElement> getTimeline() {
        return timeline;
    }
    public final HSelectionModel<HTimelineElement> getTimelineSelectionModel() {
        return timelineSelectionModel;
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

    private static class PathCompiler {

        private List<HTimelineElement> timeline;
        private int index = 0;

        private PathCompiler(List<HTimelineElement> timeline) {
            this.timeline = timeline;
        }

        /**
         * This method should start when the index is 0 or it is 1 place
         * past a waypont.
         */
        private HolonomicWaypoint eatWaypoint() {
            List<InitialGuessPoint> initialGuessPoints = new ArrayList<>();
            List<Obstacle> obstacles = new ArrayList<>();
            while (!timeline.get(index).isWaypoint()) {
                if (timeline.get(index).isInitialGuessPoint()) {
                    initialGuessPoints.add(((HInitialGuessPoint) timeline.get(index)).toInitialGuessPoint());
                } else if (timeline.get(index).isObstacle()) {
                    obstacles.add(((HObstacle) timeline.get(index)).toObstacle());
                }
                index++;
            }
            HWaypoint waypoint = (HWaypoint) timeline.get(index);
            index++;
            return waypoint.toWaypoint(initialGuessPoints, obstacles);
        }

        private HolonomicPath compile() {
            List<HolonomicWaypoint> waypoints = new ArrayList<>();
            while (index < timeline.size()) {
                waypoints.add(eatWaypoint());
            }
            return new HolonomicPath(waypoints);
        }
    }
    public HolonomicPath toPath() {
        return new PathCompiler(timeline).compile();
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