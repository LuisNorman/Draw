package controller;

import controller.command.ICommand;

import java.util.LinkedList;
import java.util.List;

// State pattern
public class CommandHistory {
    static List<ICommand> commandList = new LinkedList<>();

    public static void add(ICommand command) {
        commandList.add(command);
    }

    public List<ICommand> getCommandHistory() {
        return commandList;
    }

}
