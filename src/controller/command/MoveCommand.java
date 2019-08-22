package controller.command;

import java.util.List;

import controller.*;
import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;

// Command Pattern
// Move shape by removing and redrawing in new location
public class MoveCommand implements ICommand, IUndoRedoCommand {
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
        this.originalEndPoints = selectedShape.getLocation().getStartPoint();
    }

    // In order to move the shape, we first remove it
    // then redraw the shape at new location.
    @Override
    public void execute() {
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
        for (IShape currentShape : selectedShapes) {
            Point startPoint = currentShape.getLocation().getStartPoint();
            Point endPoint = currentShape.getLocation().getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, deltaX, deltaY);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);



            // Initialize remove strategy
            IShapeStrategy iShapeStrategy = null;

            System.out.println("Moving "+currentShape.getShapeType());

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
            // Remove shape.
            Shape shape = new Shape(paintCanvas, currentShape);
            shape.remove(iShapeStrategy);

            // Update location
            ShapeList.updateStartPoint(currentShape, newStartPoint);
            ShapeList.updateEndPoint(currentShape, newEndPoint);
            // Update selected shape location
            if (currentShape == selectedShape) {
                setSelectedShape(currentShape);
            }

            // Recreate Shape
            shape.recreate(iShapeStrategy);

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

    private void setSelectedShape(IShape shape) {
        this.selectedShape = shape;
    }


    public void undo() {
        System.out.println("Undoing move");
        // get moved shapes and its original positions
        List<IShape> movedShapes = this.getMovedShapes();
        Point endPoint = this.getOriginalEndPoint();
        IShape selectedShape = this.getSelectedShape();
        MoveCommand moveCommand = new MoveCommand(paintCanvas, endPoint, movedShapes, selectedShape);
        UndoCommandHistory.add(moveCommand);
        moveCommand.execute();
    }

    public void redo() {
        System.out.println("Redoing move");
        // get moved shapes and its original positions
        List<IShape> movedShapes = this.getMovedShapes();
        Point endPoint = this.getOriginalEndPoint();
        IShape selectedShape = this.getSelectedShape();
        MoveCommand moveCommand = new MoveCommand(paintCanvas, endPoint, movedShapes, selectedShape);
        CommandHistory.add(moveCommand);
        moveCommand.execute();
    }

}







