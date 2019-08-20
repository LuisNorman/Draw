package controller;

import controller.command.ICommand;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CommandHistory {
    private static Stack<ICommand> commandStack = new Stack<>();

    public static void add(ICommand command) {
        commandStack.push(command);
    }

    public static ICommand getLatestCommand() {
        try {
            return commandStack.pop();
        } catch (Exception e) {
            System.out.println("There are no commands in history.");
            return null;
        }
    }

}
