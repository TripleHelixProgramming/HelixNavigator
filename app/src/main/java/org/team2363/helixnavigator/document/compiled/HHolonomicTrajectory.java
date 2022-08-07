package org.team2363.helixnavigator.document.compiled;

import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONEntry;

public class HHolonomicTrajectory extends HTrajectory {

    public final List<HHolonomicTrajectorySample> holonomicSamples;

    @DeserializedJSONConstructor
    public HHolonomicTrajectory(@DeserializedJSONEntry List<HHolonomicTrajectorySample> holonomicSamples) {
        super(holonomicSamples);

        this.holonomicSamples = holonomicSamples;
    }

    // public static HHolonomicTrajectory fromTrajectory(HolonomicTrajectory trajectory) {
    //     List<HHolonomicTrajectorySample> hSamples = new ArrayList<>();
    //     for (int i = 0; i < trajectory.samples.size(); i++) {
    //         hSamples.add(HHolonomicTrajectorySample.fromTrajectorySample(trajectory.samples.get(i)));
    //     }
    //     return new HHolonomicTrajectory(hSamples);
    // }
}