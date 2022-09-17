package org.team2363.helixnavigator.document.compiled.command;

import org.team2363.helixnavigator.document.compiled.HTrajectory;

import com.jlbabilino.json.JSONSerializable;
import com.jlbabilino.json.SerializedJSONEntry;
import com.jlbabilino.json.JSONEntry.JSONType;

@JSONSerializable(JSONType.STRING)
public class HTrajectoryIDLink {
    
    @SerializedJSONEntry
    public final String id;
    public final HTrajectory trajectory;

    public HTrajectoryIDLink(String id, HTrajectory trajectory) {
        this.id = id;
        this.trajectory = trajectory;
    }
}