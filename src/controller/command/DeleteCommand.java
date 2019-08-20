package controller.command;

import java.util.ArrayList;
import java.util.List;
import controller.*;
import model.interfaces.IShape;
import model.persistence.ShapeGroup;
import model.persistence.Groups;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

public class DeleteCommand implements ICommand {
    private PaintCanvasBase paintCanvas;
    private final static String commandName = "Delete";
    private List<IShape> deletedShapes;

    public DeleteCommand(PaintCanvasBase paintCanvas, List<IShape> deletedShapes) {
        this.paintCanvas = paintCanvas;
        this.deletedShapes = deletedShapes;
    }

    public DeleteCommand(PaintCanvasBase paintCanvas, IShape deletedShape) {
        this.paintCanvas = paintCanvas;
        this.deletedShapes = new ArrayList<>();
        deletedShapes.add(deletedShape);
    }

    @Override
    public void execute() {
        addShapesInGroup(deletedShapes);
        for (IShape shape : deletedShapes) {
            System.out.println("deleting "+shape.getShapeType()+" at"+shape.getLocation().getStartPoint().getX());
            Remover remover = new Remover(paintCanvas, shape);
            IRemoveStrategy iRemoveStrategy = null;

            switch(shape.getShapeType()) {
                case TRIANGLE:
                    iRemoveStrategy = new RemoveTriangleStrategy();
                    break;

                case RECTANGLE:
                    iRemoveStrategy = new RemoveRectangleStrategy();
                    break;

                case ELLIPSE:
                    iRemoveStrategy = new RemoveEllipseStrategy();
                    break;
            }
            remover.remove(iRemoveStrategy);
            ShapeList.remove(shape);
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

}
