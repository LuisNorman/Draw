package controller;

import java.util.List;
import model.persistence.Location;
import model.interfaces.IShape;
import model.persistence.SelectedShape;
import model.persistence.SelectedShapes;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

// Command Pattern
class MoveCommand implements ICommand{
    private PaintCanvasBase paintCanvasBase;
    private Point newPoint;
    static final private String commandName = "Move";

    MoveCommand(PaintCanvasBase paintCanvasBase, Point newPoint) {
        this.paintCanvasBase = paintCanvasBase;
        this.newPoint = newPoint;
    }

    @Override
    public void execute() {
        List<IShape> selectedShapes = SelectedShapes.getAll();
        // Check if there are selected shapes. If not, do nothing.
        if (selectedShapes == null || selectedShapes.size() == 0) {
            System.out.println("Unable to move. No shapes selected.");
            return;
        }
        IShape selectedShape = SelectedShape.get();
        // Compute the distance to be moved.
        int deltaX = newPoint.getX() - selectedShape.getLocation().getStartPoint().getX();
        int deltaY = newPoint.getY() - selectedShape.getLocation().getStartPoint().getY();
        // Loop the selected shapes and grab its properties
        for (IShape shape : selectedShapes) {
            String shapeName = shape.getShapeName();
            Location location = shape.getLocation();
            Point startPoint = location.getStartPoint();
            Point endPoint = location.getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, deltaX, deltaY);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);

            // Remove and recreate shape.
            Remover remover = new Remover(paintCanvasBase, startPoint, endPoint);
            IRemoveStrategy iRemoveStrategy = null;

            Recreator recreator = new Recreator(paintCanvasBase, newStartPoint, newEndPoint, shape);
            IRecreateStrategy iRecreateStrategy = null;

            switch(shapeName) {
                case "Triangle" :
                    iRemoveStrategy = new RemoveTriangleStrategy();
                    iRecreateStrategy = new RecreateTriangleStrategy();
                    break;

                case "Rectangle" :
                    iRemoveStrategy = new RemoveRectangleStrategy();
                    iRecreateStrategy = new RecreateRectangleStrategy();
                    break;

                case "Ellipse" :
                    iRemoveStrategy = new RemoveEllipseStrategy();
                    iRecreateStrategy = new RecreateEllipseStrategy();
                    break;
            }
            if (iRemoveStrategy != null && iRecreateStrategy != null) {
                remover.remove(iRemoveStrategy);
                recreator.recreate(iRecreateStrategy);
            }

            ShapeList.updateStartPoint(shape, newStartPoint);
            ShapeList.updateEndPoint(shape, newEndPoint);
        }
    }

    // Helper function to calculate new start point when moving.
    private static Point getNewStartPoint(Point startPoint, int deltaX, int deltaY) {
        int newStartPoint_X = startPoint.getX()+deltaX;
        int newStartPoint_Y = startPoint.getY()+deltaY;
        return new Point(newStartPoint_X, newStartPoint_Y);
    }

    // Helper function to calculate new end point when moving.
    private static  Point getNewEndPoint(Point newStartPoint, int width, int height) {
        int newEndPoint_X = newStartPoint.getX()+width;
        int newEndPoint_Y = newStartPoint.getY()+height;
        return new Point(newEndPoint_X, newEndPoint_Y);
    }

}
