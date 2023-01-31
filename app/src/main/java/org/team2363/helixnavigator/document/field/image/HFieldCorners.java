package org.team2363.helixnavigator.document.field.image;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HFieldCorners {
    
    @SerializedJSONObjectValue(key = "top-left")
    public final HFieldCorner topLeftCorner;
    @SerializedJSONObjectValue(key = "bottom-right")
    public final HFieldCorner bottomRightCorner;

    @DeserializedJSONConstructor
    public HFieldCorners(
            @DeserializedJSONObjectValue(key = "top-left") HFieldCorner topLeftCorner,
            @DeserializedJSONObjectValue(key = "bottom-right") HFieldCorner bottomRightCorner) {
        this.topLeftCorner = topLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }
}
