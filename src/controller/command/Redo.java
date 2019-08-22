
package controller.command;

import model.persistence.UndoCommandHistory;

public class Redo implements ICommand{


    @Override
    public void execute() {
        ICommand lastCommand = UndoCommandHistory.getLatestCommand();

        // This can be broken if someone selects before drawing
        if (lastCommand.equals(new NullCommand())) {
            System.out.println("There are no more commands to undo.");
            return;
        }
        while (lastCommand.getCommandName().equals("Select")) {
            lastCommand = UndoCommandHistory.getLatestCommand();
        }

        switch(lastCommand.getCommandName()) {
            case "Draw":
                DrawCommand mostRecentDrawCommand = (DrawCommand)lastCommand;
                mostRecentDrawCommand.redo();
                break;

            case "Paste":
                System.out.println("Undoing paste");
                PasteCommand mostRecentPasteCommand = (PasteCommand)lastCommand;
                mostRecentPasteCommand.redo();
                break;

            case "Move":
                System.out.println("Undoing move");
                MoveCommand mostRecentMoveCommand = (MoveCommand)lastCommand;
                mostRecentMoveCommand.redo();
                break;

            case "Group":
                System.out.println("Undoing group");
                GroupCommand mostRecentGroupCommand = (GroupCommand)lastCommand;
                mostRecentGroupCommand.redo();
                break;

            case "Ungroup":
                System.out.println("Undoing ungroup");
                // Get ungrouped shapes and the group it belonged to;
                UngroupCommand mostRecentUngroupCommand = (UngroupCommand)lastCommand;
                mostRecentUngroupCommand.redo();
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
