package org.team2363.helixnavigator.document.compiled;

import java.util.List;

import com.jlbabilino.json.JSONEntry.JSONType;
import com.jlbabilino.json.JSONDeserializable;
import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;

@JSONSerializable(JSONType.ARRAY)
@JSONDeserializable(JSONType.ARRAY)
public abstract class HTrajectory {

    public final double duration;

    @SerializedJSONEntry
    public final List<? extends HTrajectorySample> samples;

    protected HTrajectory(List<? extends HTrajectorySample> samples) {
        this.samples = samples;

        duration = samples.get(samples.size() - 1).timestamp;
    }

    private int findSampleIndex(double timestamp) {
        int index;
        if (timestamp <= 0.0) {
            index = -1;
        } else if (timestamp >= this.duration) {
            index = samples.size() - 1;
        } else {
            boolean found = false;
            int foundIndex = -1;
            int start = 0;
            int end = samples.size() - 1;
            while (!found) {
                int testIndex = (start + end) / 2;
                double valAtTest = samples.get(testIndex).timestamp;
                if (timestamp > valAtTest) {
                    if (end - start <= 0) {
                        foundIndex = end + 1;
                        found = true;
                    } else {
                        start = testIndex + 1;
                    }
                } else if (timestamp < valAtTest) {
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
            index = foundIndex - 1;
        }
        return index;
    }

    public HTrajectorySample sampleAtTimestamp(double timestamp) {
        int leftIndex = findSampleIndex(timestamp);
        if (leftIndex < 0) {
            return samples.get(0);
        }

        int rightIndex = leftIndex + 1;
        if (rightIndex > samples.size() - 1) {
            return samples.get(samples.size() - 1);
        }

        return HTrajectorySample.interpolate(samples.get(leftIndex), samples.get(rightIndex), timestamp);
    }

    // public static HHolonomicTrajectory fromTrajectory(HolonomicTrajectory trajectory) {
    //     List<HHolonomicTrajectorySample> hSamples = new ArrayList<>();
    //     for (int i = 0; i < trajectory.samples.size(); i++) {
    //         hSamples.add(HHolonomicTrajectorySample.fromTrajectorySample(trajectory.samples.get(i)));
    //     }
    //     return new HHolonomicTrajectory(hSamples);
    // }
}