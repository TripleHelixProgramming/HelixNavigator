package org.team2363.helixnavigator.document.compiled.command;

import java.util.Collection;
import java.util.List;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;

public class HSequentialCommand extends HCommand {

    public final List<? extends HCommand> sequentialCommands;
    
    @DeserializedJSONConstructor
    public HSequentialCommand(
            @DeserializedJSONObjectValue(key = "sequential_commands") HCommand... sequentialCommands) {
        this.sequentialCommands = List.of(sequentialCommands);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.SEQUENTIAL;
    }
    @Override
    public boolean isSequential() {
        return true;
    }

    @Override
    public double getDuration() {
        double duration = 0.0;
        for (HCommand command : sequentialCommands) {
            duration += command.getDuration();
        }
        return duration;
    }

    @Override
    public Collection<HCommand> getRunningSubCommands(double timestamp) {
        if (timestamp < 0.0) {
            return List.of();
        }
        HCommand currentCommand = null;
        double rollingTimestamp = 0.0;
        for (int index = 0; index < sequentialCommands.size() + 1; index++) {
            if (timestamp < rollingTimestamp) {
                currentCommand = sequentialCommands.get(index - 1);
                break;
            }
            rollingTimestamp += sequentialCommands.get(index).getDuration();
        }
        return List.of(currentCommand);
    }
}
