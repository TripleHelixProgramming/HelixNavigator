package org.team2363.helixnavigator.document.command;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HCompiledAutoRoutine {

    @SerializedJSONObjectValue(key = "command")
    public final HCommand command;
    @Serialized

    @DeserializedJSONConstructor
    public HCompiledAutoRoutine(
            @DeserializedJSONObjectValue(key = "command") HCommand command,
            @DeserializedJSONObjectValue(key = "trajectories") Map<String, 
    )
}