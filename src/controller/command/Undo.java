package controller.command;

import model.persistence.CommandHistory;


public class Undo implements ICommand{

    @Override
    public void execute() {
        ICommand lastCommand = CommandHistory.getLatestCommand();

        // This can be broken if someone selects before drawing
        if (lastCommand.equals(new NullCommand())) {
            System.out.println("There are no more commands to undo.");
            return;
        }
        while (lastCommand.getCommandName().equals("Select")) {
            lastCommand = CommandHistory.getLatestCommand();
        }

        switch(lastCommand.getCommandName()) {
            case "Draw":
                DrawCommand mostRecentDrawCommand = (DrawCommand)lastCommand;
                mostRecentDrawCommand.undo();
                break;

            case "Paste":
                System.out.println("Undoing paste");
                PasteCommand mostRecentPasteCommand = (PasteCommand)lastCommand;
                mostRecentPasteCommand.undo();
                break;

            case "Move":
                System.out.println("Undoing move");
                MoveCommand mostRecentMoveCommand = (MoveCommand)lastCommand;
                mostRecentMoveCommand.undo();
                break;

            case "Group":
                System.out.println("Undoing group");
                GroupCommand mostRecentGroupCommand = (GroupCommand)lastCommand;
                mostRecentGroupCommand.undo();
                break;

            case "Ungroup":
                System.out.println("Undoing ungroup");
                // Get ungrouped shapes and the group it belonged to;
                UngroupCommand mostRecentUngroupCommand = (UngroupCommand)lastCommand;
                mostRecentUngroupCommand.undo();
                break;

            case "Delete":
                System.out.println("Undoing delete");
                DeleteCommand mostRecentDeleteCommand = (DeleteCommand)lastCommand;
                mostRecentDeleteCommand.undo();
                break;

            default:
                System.out.println("Can't undo "+lastCommand.getCommandName());
        }
    }

    @Override
    public String getCommandName() {
        return null;
    }
}
