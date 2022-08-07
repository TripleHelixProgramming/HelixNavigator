package org.team2363.helixnavigator.document.command;

public class HSequentialCommand extends HCommand {
    

    @Override
    public CommandType getCommandType() {
        return CommandType.SEQUENTIAL;
    }
    @Override
    public boolean isSequential() {
        return true;
    }
}
