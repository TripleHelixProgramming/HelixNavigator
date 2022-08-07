package org.team2363.helixnavigator.document.command;

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
}