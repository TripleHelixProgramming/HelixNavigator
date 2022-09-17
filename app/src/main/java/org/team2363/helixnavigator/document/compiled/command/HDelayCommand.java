package org.team2363.helixnavigator.document.compiled.command;

import java.util.Collection;
import java.util.Collections;

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
    public double getDuration() {
        return duration;
    }

    @Override
    public Collection<HCommand> getRunningSubCommands(double timestamp) {
        return Collections.emptyList();
    }
}