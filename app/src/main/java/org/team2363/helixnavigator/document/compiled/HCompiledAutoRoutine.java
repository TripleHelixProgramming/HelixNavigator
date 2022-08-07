package org.team2363.helixnavigator.document.compiled;

import java.util.Collections;
import java.util.Map;

import org.team2363.helixnavigator.document.compiled.command.HCommand;

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
    public final HCommand command;
    @SerializedJSONObjectValue(key = "trajectories")
    public final Map<String, HTrajectory> trajectoriesMap;
    
    public final double duration;
    private final DoubleProperty timestamp = new SimpleDoubleProperty(this, "timestamp", 0.0);
    public final PositionState positionState;
    private final ReadOnlyObjectProperty<VelocityState> velocityState = new ReadOnlyObjectWrapper<>(this, "velocityState", null);

    @DeserializedJSONConstructor
    public HCompiledAutoRoutine(
            @DeserializedJSONObjectValue(key = "command") HCommand command,
            @DeserializedJSONObjectValue(key = "trajectories") Map<String, HTrajectory> trajectoriesMap) {
        this.command = command;
        this.trajectoriesMap = Collections.unmodifiableMap(trajectoriesMap);

        this.duration = command.calculateDuration();

    }

    private void updateRobotState() {
        
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