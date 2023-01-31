package org.team2363.helixnavigator.document.field.image;

import com.jlbabilino.json.DeserializedJSONArrayItem;
import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONArrayItem;
import com.jlbabilino.json.JSONEntry.JSONType;

@JSONSerializable(JSONType.ARRAY)
@JSONDeserializable({JSONType.ARRAY})
public class HFieldSize {

    @SerializedJSONArrayItem(index = 0)
    public final double width;
    @SerializedJSONArrayItem(index = 1)
    public final double height;

    @DeserializedJSONConstructor
    public HFieldSize(
            @DeserializedJSONArrayItem(index = 0) double width,
            @DeserializedJSONArrayItem(index = 1) double height) {
        this.width = width;
        this.height = height;
    }
}
