package controller.command;
import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;
import java.util.HashMap;
import java.util.List;

public class UndoCommand implements ICommand {
    private final static  String commandName = "Undo";
    private PaintCanvasBase paintCanvas;

    public UndoCommand(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void execute() {
        ICommand lastCommand = CommandHistory.getLatestCommand();

        // This can be broken if someone selects before drawing
        if (lastCommand == null) {
            System.out.println("There are no more commands to undo.");
            return;
        }
        while (lastCommand.getCommandName().equals("Select")) {
            lastCommand = CommandHistory.getLatestCommand();
        }

        // Initialize delete command to be shared
        DeleteCommand deleteCommand = null;

        switch(lastCommand.getCommandName()) {
            case "Draw":
                System.out.println("Undoing draw");
                // Add this (DrawCommand) to Redo Command History
                DrawCommand mostRecentDrawCommand = (DrawCommand)lastCommand;
                List<IShape> shapes = mostRecentDrawCommand.getDrawnShapes();
                deleteCommand = new DeleteCommand(paintCanvas, shapes);
                UndoCommandHistory.add(deleteCommand);
                deleteCommand.execute();
                break;

            case "Paste":
                System.out.println("Undoing paste");
                // Add this to Redo Command History
                PasteCommand mostRecentPasteCommand = (PasteCommand)lastCommand;
                // Get pasted shapes.
                List<IShape> pastedShapes = mostRecentPasteCommand.getShapes();
                // Remove pasted shapes.
                deleteCommand = new DeleteCommand(paintCanvas, pastedShapes);
                UndoCommandHistory.add(deleteCommand);
                deleteCommand.execute();
                break;

            case "Move":
                System.out.println("Undoing move");
                MoveCommand mostRecentMoveCommand = (MoveCommand)lastCommand;
                // get moved shapes and its original positions
                List<IShape> movedShapes = mostRecentMoveCommand.getMovedShapes();
                Point endPoint = mostRecentMoveCommand.getOriginalEndPoint();
                IShape selectedShape = mostRecentMoveCommand.getSelectedShape();
                MoveCommand moveCommand = new MoveCommand(paintCanvas, endPoint, movedShapes, selectedShape);
                UndoCommandHistory.add(moveCommand);
                moveCommand.execute();
                break;

            case "Group":
                System.out.println("Undoing group");
                GroupCommand mostRecentGroupCommand = (GroupCommand)lastCommand;
                // Get grouped shapes.
                List<IShape> groupedShapes = mostRecentGroupCommand.getGroupedShapes();
                // Now ungroup each shape
                UngroupCommand ungroupCommand = new UngroupCommand(groupedShapes);
                UndoCommandHistory.add(ungroupCommand);
                ungroupCommand.execute();
                break;

            case "Ungroup":
                System.out.println("Undoing ungroup");
                // Get ungrouped shapes and the group it belonged to;
                UngroupCommand mostRecentUngroupCommand = (UngroupCommand) lastCommand;
                HashMap<IShape, ShapeGroup> ungroupedShapes = mostRecentUngroupCommand.getUngroupedShapes();
                GroupCommand groupCommand = new GroupCommand(ungroupedShapes);
                UndoCommandHistory.add(groupCommand);
                groupCommand.execute();
                break;

            case "Delete":
                System.out.println("Undoing delete");
                DeleteCommand mostRecentDeleteCommand = (DeleteCommand)lastCommand;
                // Get deleted shapes.
                List<IShape> deletedShapes = mostRecentDeleteCommand.getDeletedShapes();
                DrawCommand drawCommand = new DrawCommand(paintCanvas, deletedShapes);
                UndoCommandHistory.add(drawCommand);
                drawCommand.execute();
                break;

            default:
                System.out.println("Can't undo "+lastCommand.getCommandName());
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }


}
