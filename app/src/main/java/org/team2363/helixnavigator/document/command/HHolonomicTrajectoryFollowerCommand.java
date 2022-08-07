package org.team2363.helixnavigator.document.command;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.SerializedJSONObjectValue;

public class HHolonomicTrajectoryFollowerCommand extends HCommand {
    
    @SerializedJSONObjectValue(key = "trajectory_id")
    public final String trajectoryID;

    public HHolonomicTrajectoryFollowerCommand(
            @DeserializedJSONObjectValue(key = "trajectory_id") String trajectoryID) {
        this.trajectoryID = trajectoryID;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.HOLONOMIC_TRAJECTORY_FOLLOWER;
    }
    @Override
    public boolean isHolonomicTrajectoryFollower() {
        return true;
    }
}