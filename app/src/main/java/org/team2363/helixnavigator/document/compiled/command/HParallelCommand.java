package org.team2363.helixnavigator.document.compiled.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.SerializedJSONObjectValue;

public class HParallelCommand extends HCommand {

    @SerializedJSONObjectValue(key = "deadline_command")
    public final HCommand deadlineCommand;
    @SerializedJSONObjectValue(key = "parallel_commands")
    public final Collection<HCommand> parallelCommands;

    public HParallelCommand(
            @DeserializedJSONObjectValue(key = "deadline_command") HCommand deadlineCommand,
            @DeserializedJSONObjectValue(key = "parallel_commands") HCommand... parallelCommands) {
        this.deadlineCommand = deadlineCommand;
        this.parallelCommands = List.of(parallelCommands);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.PARALLEL;
    }
    @Override
    public boolean isParallel() {
        return true;
    }

    @Override
    public double getDuration() {
        return deadlineCommand.getDuration();
    }

    @Override
    public Collection<HCommand> getRunningSubCommands(double timestamp) {
        List<HCommand> runningCommands = new ArrayList<>();
        if (deadlineCommand.isRunning(timestamp)) {
            runningCommands.add(deadlineCommand);
            for (HCommand parallelCommand : parallelCommands) {
                if (parallelCommand.isRunning(timestamp)) {
                    runningCommands.add(parallelCommand);
                }
            }
        }
        return runningCommands;
    }
}
