package org.team2363.helixnavigator.document.compiled.command;

import java.util.Collection;

import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.SerializedJSONObjectValue;

@JSONSerializable(JSONType.OBJECT)
public abstract class HCommand {

    @JSONSerializable(JSONType.STRING)
    public static enum CommandType {
        PARALLEL, SEQUENTIAL, DELAY, HOLONOMIC_TRAJECTORY_FOLLOWER;

        @SerializedJSONEntry
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    @SerializedJSONObjectValue(key = "command_type")
    public abstract CommandType getCommandType();
    public boolean isParallel() {
        return false;
    }
    public boolean isSequential() {
        return false;
    }
    public boolean isDelay() {
        return false;
    }
    public boolean isHolonomicTrajectoryFollower() {
        return false;
    }
    public boolean isCustom() {
        return false;
    }

    public abstract double getDuration();
    public boolean isRunning(double timestamp) {
        return timestamp <= getDuration();
    }
    public abstract Collection<HCommand> getRunningSubCommands(double timestamp);
}