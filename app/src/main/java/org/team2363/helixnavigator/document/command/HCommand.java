package org.team2363.helixnavigator.document.command;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONDeterminer;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONDeserializerException;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public abstract class HCommand {

    @JSONSerializable(JSONType.STRING)
    @JSONDeserializable({JSONType.STRING})
    public static enum CommandType {
        PARALLEL, SEQUENTIAL, DELAY, HOLONOMIC_TRAJECTORY_FOLLOWER, CUSTOM;

        @DeserializedJSONConstructor
        public static CommandType forName(@DeserializedJSONEntry String name) {
            return valueOf(name.toUpperCase());
        }

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

    @DeserializedJSONDeterminer
    public static Class<? extends HCommand> commandDeterminer(@DeserializedJSONObjectValue(key = "command_type") CommandType commandType)
            throws JSONDeserializerException {
        switch (commandType) {
            case PARALLEL:
                return HParallelCommand.class;
            case SEQUENTIAL:
                return HSequentialCommand.class;
            case DELAY:
                return HDelayCommand.class;
            case HOLONOMIC_TRAJECTORY_FOLLOWER:
                return HHolonomicTrajectoryFollowerCommand.class;
            case CUSTOM:
                return HCustomCommand.class;
            default:
                throw new JSONDeserializerException("Cannot have null command type");
        }
    }
}