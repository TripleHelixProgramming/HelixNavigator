package org.team2363.helixnavigator.document.compiled;

import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

@JSONSerializable(JSONType.OBJECT)
@JSONDeserializable({JSONType.OBJECT})
public class HTrajectorySample {

    @SerializedJSONObjectValue(key = "timestamp")
    public final double timestamp;
    @SerializedJSONObjectValue(key = "x")
    public final double x;
    @SerializedJSONObjectValue(key = "y")
    public final double y;
    @SerializedJSONObjectValue(key = "heading")
    public final double heading;

    protected HTrajectorySample(double timestamp, double x, double y, double heading) {
        this.timestamp = timestamp;

        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    static HTrajectorySample interpolate(HTrajectorySample a, HTrajectorySample b, double timestamp) {
        if (a instanceof HHolonomicTrajectorySample && b instanceof HHolonomicTrajectorySample) {
            return HHolonomicTrajectorySample.interpolate(a, b, timestamp);
        } else {
            return null;
        }
    }

}