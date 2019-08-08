package controller;

import java.util.List;
import model.interfaces.IShape;
import model.persistence.Location;
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
            String shapeName = shape.getShapeName();
            Location location = shape.getLocation();
            Point startPoint = location.getStartPoint();
            Point endPoint = location.getEndPoint();
            Remover remover = new Remover(paintCanvas, startPoint, endPoint);
            IRemoveStrategy iRemoveStrategy = null;

            switch(shapeName) {
                case "Triangle" :
                    iRemoveStrategy = new RemoveTriangleStrategy();
                    break;

                case "Rectangle" :
                    iRemoveStrategy = new RemoveRectangleStrategy();
                    break;

                case "Ellipse" :
                    iRemoveStrategy = new RemoveEllipseStrategy();
                    break;
            }
            remover.remove(iRemoveStrategy);
            ShapeList.remove(shape);
        }
    }

}
