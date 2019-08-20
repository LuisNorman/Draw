package controller;

import controller.command.ICommand;

import java.util.Stack;

public class UndoCommandHistory {
    private static Stack<ICommand> commandStack = new Stack<>();

    public static void add(ICommand command) {
        commandStack.push(command);
    }

    public static ICommand getLatestCommand() {
        if (commandStack.peek() != null) {
            return commandStack.pop();
        }
        else {
            return null;
        }
    }

    public static boolean contains(ICommand command) {
        return commandStack.contains(command);
    }

}
