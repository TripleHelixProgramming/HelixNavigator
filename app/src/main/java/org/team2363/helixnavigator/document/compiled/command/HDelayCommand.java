package org.team2363.helixnavigator.document.compiled.command;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.SerializedJSONObjectValue;

public class HDelayCommand extends HCommand {

    @SerializedJSONObjectValue(key = "duration")
    public final double duration;
    
    public HDelayCommand(@DeserializedJSONObjectValue(key = "duration") double duration) {
        this.duration = duration;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.DELAY;
    }
    @Override
    public boolean isDelay() {
        return true;
    }

    @Override
    public double calculateDuration() {
        return duration;
    }

    @Override
    public Collection<? extends HCommand> getRunningCommands(double timestamp) {
        return timestamp <= duration ? List.of(this) : Collections.emptyList();
    }
}