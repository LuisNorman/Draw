package model.persistence;

import controller.command.ICommand;
import controller.command.NullCommand;

import java.util.Stack;

public class UndoCommandHistory {
    private static Stack<ICommand> commandStack = new Stack<>();

    public static void add(ICommand command) {
        commandStack.push(command);
    }

    public static ICommand getLatestCommand() {
        try {
            return commandStack.pop();
        } catch (Exception e) {
            System.out.println("There are no commands in history.");
            return new NullCommand();
        }
    }

    public static boolean contains(ICommand command) {
        return commandStack.contains(command);
    }

}
