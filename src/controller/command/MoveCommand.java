package controller.command;

import java.util.List;

import controller.*;
import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;

// Command Pattern
// Move shape by removing and redrawing in new location
public class MoveCommand implements ICommand {
    private PaintCanvasBase paintCanvas;
    private Point newPoint;
    static final private String commandName = "Move";
    private List<IShape> movedShapes;
    private List<IShape> selectedShapes;
    private IShape selectedShape;
    private Point originalEndPoints;

    public MoveCommand(PaintCanvasBase paintCanvas, Point newPoint, List<IShape> selectedShapes, IShape selectedShape) {
        this.paintCanvas = paintCanvas;
        this.newPoint = newPoint;
        this.selectedShapes = selectedShapes;
        this.selectedShape = selectedShape;
    }

    // In order to move the shape, we first remove it
    // then redraw the shape at new location.
    @Override
    public void execute() {
//        List<IShape> selectedShapes = SelectedShapes.getAll();
        // Check if there are selected shapes. If not, do nothing.
        if (selectedShapes == null || selectedShapes.size() == 0) {
            System.out.println("Unable to move. No shapes selected.");
            return;
        }

        // Check if selected shapes are in group
        // If they are, move them as well.
        addShapesInGroup(selectedShapes);

        // store moved shapes
        setMovedShapes(selectedShapes);

        // Compute the distance to be moved.
//        IShape selectedShape = SelectedShape.get();
        int deltaX = newPoint.getX() - selectedShape.getLocation().getStartPoint().getX();
        int deltaY = newPoint.getY() - selectedShape.getLocation().getStartPoint().getY();
        // Loop the selected shapes and grab its properties
        for (IShape shape : selectedShapes) {
            Point startPoint = shape.getLocation().getStartPoint();
            Point endPoint = shape.getLocation().getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, deltaX, deltaY);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);
            // Initialize remove strategy
            IRemoveStrategy iRemoveStrategy = null;
            // Initialize recreate strategy
            IRecreateStrategy iRecreateStrategy = null;

            System.out.println("Moving "+shape.getShapeType());

            switch(shape.getShapeType()) {
                case TRIANGLE:
                    iRemoveStrategy = new RemoveTriangleStrategy();
                    iRecreateStrategy = new RecreateTriangleStrategy();
                    break;

                case RECTANGLE:
                    iRemoveStrategy = new RemoveRectangleStrategy();
                    iRecreateStrategy = new RecreateRectangleStrategy();
                    break;

                case ELLIPSE:
                    iRemoveStrategy = new RemoveEllipseStrategy();
                    iRecreateStrategy = new RecreateEllipseStrategy();
                    break;
            }
            // Remove shape.
            Remover remover = new Remover(paintCanvas, shape);
            remover.remove(iRemoveStrategy);

            // Update location
            ShapeList.updateStartPoint(shape, newStartPoint);
            ShapeList.updateEndPoint(shape, newEndPoint);
            // Update selected shape location
            if (shape == selectedShape) {
                setSelectedShape(shape);
            }
            setOriginalEndPoints(selectedShape.getLocation().getEndPoint());

            // Recreate Shape
            Recreator recreator = new Recreator(paintCanvas, shape);
            recreator.recreate(iRecreateStrategy);

        }
    }

    @Override
    public String getCommandName() {
        return commandName;
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

    private void setMovedShapes(List<IShape> movedShapes) {
        this.movedShapes = movedShapes;
    }

    List<IShape> getMovedShapes() {
        return movedShapes;
    }

    IShape getSelectedShape(){
        return selectedShape;
    }

    Point getOriginalEndPoint() {
        return originalEndPoints;
    }

    private void setOriginalEndPoints(Point endPoints) {
        this.originalEndPoints = endPoints;
    }

    private void setSelectedShape(IShape shape) {
        this.selectedShape = shape;
    }

}







