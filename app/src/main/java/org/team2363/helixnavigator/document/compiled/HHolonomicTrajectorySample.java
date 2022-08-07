package org.team2363.helixnavigator.document.compiled;

import org.team2363.helixtrajectory.HolonomicTrajectorySample;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONObjectValue;

@JSONSerializable(JSONType.OBJECT)
public class HHolonomicTrajectorySample extends HTrajectorySample {

    @SerializedJSONObjectValue(key = "velocity_x")
    public final double velocityX;
    @SerializedJSONObjectValue(key = "velocity_y")
    public final double velocityY;
    @SerializedJSONObjectValue(key = "angular_velocity")
    public final double angularVelocity;

    @DeserializedJSONConstructor
    public HHolonomicTrajectorySample(
            @DeserializedJSONObjectValue(key = "timestamp") double timestamp,
            @DeserializedJSONObjectValue(key = "x") double x,
            @DeserializedJSONObjectValue(key = "y") double y,
            @DeserializedJSONObjectValue(key = "heading") double heading,
            @DeserializedJSONObjectValue(key = "velocity_x") double velocityX,
            @DeserializedJSONObjectValue(key = "velocity_y") double velocityY,
            @DeserializedJSONObjectValue(key = "angular_velocity") double angularVelocity) {
        super(timestamp, x, y, heading);

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.angularVelocity = angularVelocity;
    }

    static HHolonomicTrajectorySample interpolate(HHolonomicTrajectorySample a, HHolonomicTrajectorySample b, double timestamp) {
        double ratioA = (b.timestamp - timestamp) / (b.timestamp - a.timestamp);
        double ratioB = 1.0 - ratioA;
        double x = ratioA * a.x + ratioB * b.x;
        double y = ratioA * a.y + ratioB * b.y;
        double heading = ratioA * a.heading + ratioB * b.heading;
        double velocityX = ratioA * a.velocityX + ratioB * b.velocityX;
        double velocityY = ratioA * a.velocityY + ratioB * b.velocityY;
        double angularVelocity = ratioA * a.angularVelocity + ratioB * b.angularVelocity;
        return new HHolonomicTrajectorySample(timestamp, x, y, heading, velocityX, velocityY, angularVelocity);
    }

    public static HHolonomicTrajectorySample fromHolonomicTrajectorySample(HolonomicTrajectorySample sample) {
        return new HHolonomicTrajectorySample(sample.timestamp, sample.x, sample.y, sample.heading,
                sample.velocityX, sample.velocityY, sample.angularVelocity);
    }
}