package controller;

import java.util.List;
import model.interfaces.IShape;
import model.persistence.SelectedShapes;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

public class DeleteCommand implements ICommand {
    private List<IShape> selectedShapes;
    private PaintCanvasBase paintCanvas;

    public DeleteCommand(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
        selectedShapes = SelectedShapes.getAll();
    }

    @Override
    public void execute() {
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

}
