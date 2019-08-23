
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
                System.out.println("Redoing draw");
                DrawCommand mostRecentDrawCommand = (DrawCommand)lastCommand;
                mostRecentDrawCommand.redo();
                break;

            case "Paste":
                System.out.println("Redoing paste");
                PasteCommand mostRecentPasteCommand = (PasteCommand)lastCommand;
                mostRecentPasteCommand.redo();
                break;

            case "Move":
                System.out.println("Redoing move");
                MoveCommand mostRecentMoveCommand = (MoveCommand)lastCommand;
                mostRecentMoveCommand.redo();
                break;

            case "Group":
                System.out.println("Redoing group");
                GroupCommand mostRecentGroupCommand = (GroupCommand)lastCommand;
                mostRecentGroupCommand.redo();
                break;

            case "Ungroup":
                System.out.println("Redoing ungroup");
                // Get ungrouped shapes and the group it belonged to;
                UngroupCommand mostRecentUngroupCommand = (UngroupCommand)lastCommand;
                mostRecentUngroupCommand.redo();
                break;

            case "Delete":
                System.out.println("Redoing delete");
                DeleteCommand mostRecentDeleteCommand = (DeleteCommand)lastCommand;
                mostRecentDeleteCommand.redo();
                break;

            default:
                System.out.println("Can't redo "+lastCommand.getCommandName());
        }
    }

    @Override
    public String getCommandName() {
        return null;
    }
}
