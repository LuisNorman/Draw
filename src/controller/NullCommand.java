package controller;

import controller.command.ICommand;

public class NullCommand implements ICommand {
    @Override
    public void execute() {
        System.out.println("Please select a command");
    }

    @Override
    public String getCommandName() {
        return "Null Command";
    }
}
