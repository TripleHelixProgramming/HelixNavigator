package org.team2363.helixnavigator.document.compiled.command;

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
    public double calculateDuration() {
        double duration = 0.0;
        for (HCommand command : sequentialCommands) {
            duration += command.calculateDuration();
        }
        return duration;
    }
}
