package controller.command;

import model.interfaces.IShape;
import model.persistence.CommandHistory;
import model.persistence.Point;
import model.persistence.ShapeGroup;
import model.persistence.UndoCommandHistory;
import view.interfaces.PaintCanvasBase;
import java.util.HashMap;
import java.util.List;

public class RedoCommand implements ICommand {
    private final static  String commandName = "Redo";
    private PaintCanvasBase paintCanvas;

    public RedoCommand(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void execute() {
        ICommand lastCommand = UndoCommandHistory.getLatestCommand();

        // This can be broken if someone selects before drawing
        if (lastCommand.equals(new NullCommand())) {
            System.out.println("There are no more commands to redo.");
            return;
        }
        while (lastCommand.getCommandName().equals("Select")) {
            lastCommand = UndoCommandHistory.getLatestCommand();
        }
        DeleteCommand deleteCommand = null;

        switch(lastCommand.getCommandName()) {
            case "Draw":
                System.out.println("Redoing draw");
                // Add this (DrawCommand) to Redo Command History
                DrawCommand mostRecentDrawCommand = (DrawCommand)lastCommand;
                List<IShape> shapes = mostRecentDrawCommand.getDrawnShapes();
                deleteCommand = new DeleteCommand(paintCanvas, shapes);
                CommandHistory.add(deleteCommand);
                deleteCommand.execute();
                break;

            case "Paste":
                System.out.println("Redoing paste");
                // Add this to Redo Command History
                PasteCommand mostRecentPasteCommand = (PasteCommand) lastCommand;
                // Get pasted shapes.
                List<IShape> pastedShapes = mostRecentPasteCommand.getShapes();
                // Remove pasted shapes.
                deleteCommand = new DeleteCommand(paintCanvas, pastedShapes);
                CommandHistory.add(deleteCommand);
                deleteCommand.execute();
                break;

            case "Move":
                System.out.println("Redoing move");
                MoveCommand mostRecentMoveCommand = (MoveCommand)lastCommand;
                // get moved shapes and its original positions
                List<IShape> movedShapes = mostRecentMoveCommand.getMovedShapes();
                Point endPoint = mostRecentMoveCommand.getOriginalEndPoint();
                IShape selectedShape = mostRecentMoveCommand.getSelectedShape();
                MoveCommand moveCommand = new MoveCommand(paintCanvas, endPoint, movedShapes, selectedShape);
                CommandHistory.add(moveCommand);
                moveCommand.execute();
                break;

            case "Group":
                System.out.println("Redoing group");
                GroupCommand mostRecentGroupCommand = (GroupCommand)lastCommand;
                // Get grouped shapes.
                List<IShape> groupedShapes = mostRecentGroupCommand.getGroupedShapes();
                // Now ungroup each shape
                UngroupCommand ungroupCommand = new UngroupCommand(groupedShapes);
                CommandHistory.add(ungroupCommand);
                ungroupCommand.execute();
                break;

            case "Ungroup":
                System.out.println("Redoing ungroup");
                // Get ungrouped shapes and the group it belonged to;
                UngroupCommand mostRecentUngroupCommand = (UngroupCommand) lastCommand;
                HashMap<IShape, ShapeGroup> ungroupedShapes = mostRecentUngroupCommand.getUngroupedShapes();
                GroupCommand groupCommand = new GroupCommand(ungroupedShapes);
                CommandHistory.add(groupCommand);
                groupCommand.execute();
                break;

            case "Delete":
                System.out.println("Redoing delete");
                DeleteCommand mostRecentDeleteCommand = (DeleteCommand)lastCommand;
                // Get deleted shapes.
                List<IShape> deletedShapes = mostRecentDeleteCommand.getDeletedShapes();
                DrawCommand drawCommand = new DrawCommand(paintCanvas, deletedShapes);
                CommandHistory.add(drawCommand);
                drawCommand.execute();
                break;

        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
