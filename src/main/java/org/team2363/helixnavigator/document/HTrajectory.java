package org.team2363.helixnavigator.document;

import java.util.Collections;
import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;

@JSONSerializable(rootType = JSONType.ARRAY)
public class HTrajectory {

    @SerializedJSONEntry
    public final List<HTrajectorySample> samples;

    @DeserializedJSONConstructor
    public HTrajectory(@DeserializedJSONEntry List<HTrajectorySample> samples) {
        this.samples = Collections.unmodifiableList(samples);
    }
}