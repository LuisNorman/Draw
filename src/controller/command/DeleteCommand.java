package controller.command;

import java.util.List;
import controller.*;
import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;

public class DeleteCommand implements ICommand, IUndoRedoCommand {
    private PaintCanvasBase paintCanvas;
    private final static String commandName = "Delete";
    private List<IShape> deletedShapes;

    public DeleteCommand(PaintCanvasBase paintCanvas, List<IShape> deletedShapes) {
        this.paintCanvas = paintCanvas;
        this.deletedShapes = deletedShapes;
    }

    @Override
    public void execute() {
        addShapesInGroup(deletedShapes);
        for (IShape currentShape : deletedShapes) {
            System.out.println("deleting "+currentShape.getShapeType()+" at"+currentShape.getLocation().getStartPoint().getX());
            Shape shape = new Shape(paintCanvas, currentShape);
            IShapeStrategy iShapeStrategy = null;

            switch(currentShape.getShapeType()) {
                case TRIANGLE:
                    iShapeStrategy = new TriangleStrategy();
                    break;

                case RECTANGLE:
                    iShapeStrategy = new RectangleStrategy();
                    break;

                case ELLIPSE:
                    iShapeStrategy = new EllipseStrategy();
                    break;
            }
            shape.remove(iShapeStrategy);
            ShapeList.remove(currentShape);
            // Not sure if I should delete the shape from the selected shapes list
            // whenever an item is deleted from shape list.
//            SelectedShapes.remove(shape);
        }
        if (!UndoCommandHistory.contains(this)) {
            CommandHistory.add(this);
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    // Checks if a shape is apart of a group
    // If so, it will add the grouped shapes to the selected list.
    private static void addShapesInGroup(List<IShape> selectedShapes) {
        int size = selectedShapes.size();
        List<IShape> tempShapes = selectedShapes;
        for (int i=0; i<size; i++) {
            IShape currentShape = tempShapes.get(i);
            for (ShapeGroup group: Groups.getGroups()) {
                if (group.contains(currentShape)) {
                    List<IShape> shapeGroup = group.getShapeGroup();
                    for (IShape shape: shapeGroup) {
                        if (!selectedShapes.contains(shape)) {
                            selectedShapes.add(shape);
                        }
                    }
                }
            }
        }
    }

    List<IShape> getDeletedShapes() {
        return deletedShapes;
    }

    public void undo() {
        System.out.println("Undoing delete");
//        DeleteCommand mostRecentDeleteCommand = (DeleteCommand)lastCommand;
        // Get deleted shapes.
        List<IShape> deletedShapes = this.getDeletedShapes();
        DrawCommand drawCommand = new DrawCommand(paintCanvas, deletedShapes);
        UndoCommandHistory.add(drawCommand);
        drawCommand.execute();
    }

    public void redo() {
        System.out.println("Redoing delete");
//        DeleteCommand mostRecentDeleteCommand = (DeleteCommand)lastCommand;
        // Get deleted shapes.
        List<IShape> deletedShapes = this.getDeletedShapes();
        DrawCommand drawCommand = new DrawCommand(paintCanvas, deletedShapes);
        CommandHistory.add(drawCommand);
        drawCommand.execute();
    }

}
