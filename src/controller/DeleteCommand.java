package controller;

import java.util.List;
import model.interfaces.IShape;
import model.persistence.ShapeGroup;
import model.persistence.Groups;
import model.persistence.SelectedShapes;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

public class DeleteCommand implements ICommand {
    private PaintCanvasBase paintCanvas;

    public DeleteCommand(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;

    }

    @Override
    public void execute() {
        List<IShape> selectedShapes = SelectedShapes.getAll();
        addShapesInGroup(selectedShapes);
        for (IShape shape : selectedShapes) {
            Point startPoint = shape.getLocation().getStartPoint();
            Point endPoint = shape.getLocation().getEndPoint();
            Remover remover = new Remover(paintCanvas, startPoint, endPoint);
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

}
