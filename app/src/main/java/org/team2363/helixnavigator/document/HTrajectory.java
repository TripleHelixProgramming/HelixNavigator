package org.team2363.helixnavigator.document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.team2363.helixnavigator.document.timeline.HHardWaypoint;
import org.team2363.helixtrajectory.HolonomicTrajectory;
import org.team2363.helixtrajectory.HolonomicTrajectorySample;
import org.team2363.helixtrajectory.HolonomicTrajectorySegment;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONEntry;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

@JSONSerializable(JSONType.ARRAY)
@JSONDeserializable({JSONType.ARRAY})
public class HTrajectory {

    private final DoubleProperty timestamp = new SimpleDoubleProperty(this, "timestamp", 0.0);
    public final double duration;
    public final HHardWaypoint robotLocation = new HHardWaypoint();

    @SerializedJSONEntry
    public final List<HTrajectorySample> samples;

    @DeserializedJSONConstructor
    public HTrajectory(@DeserializedJSONEntry List<HTrajectorySample> samples) {
        this.samples = Collections.unmodifiableList(samples);

        duration = samples.get(samples.size() - 1).ts;

        robotLocation.setX(samples.get(0).x);
        robotLocation.setY(samples.get(0).y);
        robotLocation.setHeading(samples.get(0).heading);
        // Ripped off my own code from HSelectionModel
        timestamp.addListener((obs, oldVal, newVal) -> {
            double targetTimestamp = newVal.doubleValue();
            double newX;
            double newY;
            double newHeading;
            if (targetTimestamp <= 0.0) {
                newX = samples.get(0).x;
                newY = samples.get(0).y;
                newHeading = samples.get(0).heading;
            } else if (targetTimestamp >= this.duration) {
                newX = samples.get(samples.size() - 1).x;
                newY = samples.get(samples.size() - 1).y;
                newHeading = samples.get(samples.size() - 1).heading;
            } else {
                boolean found = false;
                int foundIndex = -1;
                int start = 0;
                int end = samples.size() - 1;
                while (!found) {
                    int testIndex = (start + end) / 2;
                    double valAtTest = samples.get(testIndex).ts;
                    if (targetTimestamp > valAtTest) {
                        if (end - start <= 0) {
                            foundIndex = end + 1;
                            found = true;
                        } else {
                            start = testIndex + 1;
                        }
                    } else if (targetTimestamp < valAtTest) {
                        if (end - start <= 0) {
                            foundIndex = start;
                            found = true;
                        } else {
                            end = testIndex - 1;
                        }
                    } else {
                        found = true;
                    }
                }
                HTrajectorySample a = samples.get(foundIndex - 1);
                HTrajectorySample b = samples.get(foundIndex);
                double ratioA = (b.ts - targetTimestamp) / (b.ts - a.ts);
                double ratioB = 1.0 - ratioA;
                newX = ratioA * a.x + ratioB * b.x;
                newY = ratioA * a.y + ratioB * b.y;
                newHeading = ratioA * a.heading + ratioB * b.heading;
            }
            robotLocation.setX(newX);
            robotLocation.setY(newY);
            robotLocation.setHeading(newHeading);
        });
    }

    public final DoubleProperty timestampProperty() {
        return timestamp;
    }
    public final void setTimestamp(double value) {
        timestamp.set(value);
    }
    public final double getTimestamp() {
        return timestamp.get();
    }

    public static HTrajectory fromTrajectory(HolonomicTrajectory trajectory) {
        List<HTrajectorySample> hSamples = new ArrayList<>();
        double timestamp = 0.0;
        for (HolonomicTrajectorySegment segment : trajectory.holonomicSegments) {
            for (HolonomicTrajectorySample sample : segment.holonomicSamples) {
                timestamp += sample.intervalDuration;
                hSamples.add(HTrajectorySample.fromTrajectorySample(sample, timestamp));
            }
        }
        return new HTrajectory(hSamples);
    }
}