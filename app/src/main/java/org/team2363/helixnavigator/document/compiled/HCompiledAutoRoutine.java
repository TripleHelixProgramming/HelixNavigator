package org.team2363.helixnavigator.document.compiled;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.team2363.helixnavigator.document.compiled.command.HCommand;
import org.team2363.helixnavigator.document.compiled.command.HHolonomicTrajectoryFollowerCommand;
import org.team2363.helixnavigator.document.compiled.command.HSequentialCommand;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HCompiledAutoRoutine {

    @SerializedJSONObjectValue(key = "command")
    public final HSequentialCommand sequentialCommand;
    @SerializedJSONObjectValue(key = "trajectories")
    public final Map<String, HTrajectory> trajectoriesMap;
    
    public final double duration;
    private final DoubleProperty timestamp = new SimpleDoubleProperty(this, "timestamp", 0.0);
    public final PositionState positionState = new PositionState();
    private final ReadOnlyObjectProperty<VelocityState> velocityState = new ReadOnlyObjectWrapper<>(this, "velocityState", null);

    @DeserializedJSONConstructor
    public HCompiledAutoRoutine(
            @DeserializedJSONObjectValue(key = "command") HSequentialCommand sequentialCommand,
            @DeserializedJSONObjectValue(key = "trajectories") Map<String, HTrajectory> trajectoriesMap) {
        this.sequentialCommand = sequentialCommand;
        this.trajectoriesMap = Collections.unmodifiableMap(trajectoriesMap);

        this.duration = sequentialCommand.getDuration();

    }

    private void updateRobotState() {
        Collection<HCommand> runningCommands = sequentialCommand.getRunningSubCommands(getTimestamp());
        followerSearch: {
            for (HCommand command : runningCommands) {
                if (command.isHolonomicTrajectoryFollower()) {
                    HHolonomicTrajectoryFollowerCommand followerCommand = (HHolonomicTrajectoryFollowerCommand) command;
                    HTrajectorySample sample = followerCommand.trajectoryID.trajectory.sampleAtTimestamp(getTimestamp());
                    positionState.applyTrajectorySample(sample);
                    break followerSearch;
                }
            }
            positionState.setX(0.0);
            positionState.setY(0.0);
            positionState.setHeading(0.0);
        }
    }

    public final DoubleProperty timestampProperty() {
        return timestamp;
    }
    public final void setTimestamp(double value) {
        timestamp.set(value);
    }
    public final double getTimestamp() {
        return timestamp.get();
    }

}