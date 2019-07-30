package controller;

import java.util.List;

// State pattern
public class CommandHistory {
    List<ICommand> commandList;

    public void add(ICommand command) {
        commandList.add(command);
    }

    public List<ICommand> getCommandHistory() {
        return commandList;
    }

}
