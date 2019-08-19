package controller.command;

import controller.command.ICommand;

public class UndoCommand implements ICommand {
    private ICommand command;

    public UndoCommand(ICommand command) {
        this.command = command;
    }

    @Override
    public void execute() {

    }
}
