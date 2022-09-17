package org.team2363.helixnavigator.document.compiled.command;

import java.util.Collection;
import java.util.Collections;

import com.jlbabilino.json.SerializedJSONObjectValue;

public class HHolonomicTrajectoryFollowerCommand extends HCommand {
    
    @SerializedJSONObjectValue(key = "trajectory_id")
    public final HTrajectoryIDLink trajectoryID;

    public HHolonomicTrajectoryFollowerCommand(HTrajectoryIDLink trajectoryID) {
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
    
    @Override
    public double getDuration() {
        return trajectoryID.trajectory.duration;
    }

    @Override
    public Collection<HCommand> getRunningSubCommands(double timestamp) {
        return Collections.emptyList();
    }
}