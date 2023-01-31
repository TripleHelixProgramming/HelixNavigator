package org.team2363.helixnavigator.document.field.image;

import com.jlbabilino.json.DeserializedJSONArrayItem;
import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONArrayItem;
import com.jlbabilino.json.JSONEntry.JSONType;

@JSONSerializable(JSONType.ARRAY)
@JSONDeserializable({JSONType.ARRAY})
public class HFieldCorner {

    @SerializedJSONArrayItem(index = 0)
    public final double x;
    @SerializedJSONArrayItem(index = 1)
    public final double y;

    @DeserializedJSONConstructor
    public HFieldCorner(
            @DeserializedJSONArrayItem(index = 0) double x,
            @DeserializedJSONArrayItem(index = 1) double y) {
        this.x = x;
        this.y = y;
    }
}
