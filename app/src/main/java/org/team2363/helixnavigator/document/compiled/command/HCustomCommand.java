package org.team2363.helixnavigator.document.compiled.command;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.SerializedJSONObjectValue;

public class HCustomCommand extends HCommand {

    @SerializedJSONObjectValue(key = "id")
    public final String id;

    @DeserializedJSONConstructor
    public HCustomCommand(@DeserializedJSONObjectValue(key = "id") String id) {
        this.id = id;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CUSTOM;
    }
    @Override
    public boolean isCustom() {
        return true;
    }
    
}
