package org.team2363.helixnavigator.document;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

@JSONSerializable
public class HTrajectorySample {

    @SerializedJSONObjectValue(key = "ts")
    public final double ts;
    @SerializedJSONObjectValue(key = "x")
    public final double x;
    @SerializedJSONObjectValue(key = "y")
    public final double y;
    @SerializedJSONObjectValue(key = "heading")
    public final double heading;
    @SerializedJSONObjectValue(key = "vx")
    public final double vx;
    @SerializedJSONObjectValue(key = "vy")
    public final double vy;
    @SerializedJSONObjectValue(key = "omega")
    public final double omega;

    @DeserializedJSONConstructor
    public HTrajectorySample(
            @DeserializedJSONObjectValue(key = "ts") double ts,
            @DeserializedJSONObjectValue(key = "x") double x,
            @DeserializedJSONObjectValue(key = "y") double y,
            @DeserializedJSONObjectValue(key = "heading") double heading,
            @DeserializedJSONObjectValue(key = "vx") double vx,
            @DeserializedJSONObjectValue(key = "vy") double vy,
            @DeserializedJSONObjectValue(key = "omega") double omega) {
        this.ts = ts;
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.vx = vx;
        this.vy = vy;
        this.omega = omega;
    }
}