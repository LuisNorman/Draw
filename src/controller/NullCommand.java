package controller;

public class NullCommand implements ICommand {
    @Override
    public void execute() {
        System.out.println("Please select a command");
    }
}
