package controller.command;

import controller.*;
import model.persistence.*;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

import java.util.LinkedList;
import java.util.List;

public class PasteCommand implements ICommand, IUndoRedoCommand {
    private final PaintCanvasBase paintCanvas;
    private final static String commandName = "Paste";
    private List<IShape> shapesToPaste;
    private static List<IShape> pastedShapes = new LinkedList<>();

    public PasteCommand(PaintCanvasBase paintCanvas, List<IShape> shapesToPaste) {
        this.paintCanvas = paintCanvas;
        this.shapesToPaste = shapesToPaste;
    }

    @Override
    public void execute() {
        if (shapesToPaste == null) {
            System.out.println("There are no shapes to paste.");
            return;
        }
        for (IShape currentShape : shapesToPaste) {
            IShape newShape = null;
            Point startPoint = currentShape.getLocation().getStartPoint();
            Point endPoint = currentShape.getLocation().getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, width*2+5);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);
            currentShape.setLocation(newStartPoint, newEndPoint);
            Shape shape = new Shape(paintCanvas, currentShape);
            IShapeStrategy iShapeStrategy = null;

            switch(currentShape.getShapeType()) {
                case TRIANGLE :
                    newShape = new Triangle(currentShape);
                    iShapeStrategy = new TriangleStrategy();
                    break;

                case RECTANGLE :
                    newShape = new Rectangle(currentShape);
                    iShapeStrategy = new RectangleStrategy();
                    break;

                case ELLIPSE :
                    newShape = new Ellipse(currentShape);
                    iShapeStrategy = new EllipseStrategy();
                    break;
            }
            shape.recreate(iShapeStrategy);

            newShape.setLocation(newStartPoint, newEndPoint);
            ShapeList.add(newShape);
            pastedShapes.add(newShape);
        }

        // Check if paste is used for undo
        if (!UndoCommandHistory.contains(this)) {
            ICommand newPasteCommand = new PasteCommand(paintCanvas, pastedShapes);
            CommandHistory.add(newPasteCommand);
        }
        // Clear pasted shapes
        pastedShapes = new LinkedList<>();
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    private static Point getNewStartPoint(Point startPoint, int deltaX) {
        int newStartPoint_X = startPoint.getX()+deltaX+5;
        int newStartPoint_Y = startPoint.getY();
        return new Point(newStartPoint_X, newStartPoint_Y);
    }

    private static  Point getNewEndPoint(Point newStartPoint, int width, int height) {
        int newEndPoint_X = newStartPoint.getX()+width;
        int newEndPoint_Y = newStartPoint.getY()+height;
        return new Point(newEndPoint_X, newEndPoint_Y);
    }

    List<IShape> getShapes() {
        return shapesToPaste;
    }

    public void undo() {
        System.out.println("Undoing paste");
        // Get pasted shapes.
        List<IShape> pastedShapes = this.getShapes();
        // Remove pasted shapes.
        DeleteCommand deleteCommand = new DeleteCommand(paintCanvas, pastedShapes);
        UndoCommandHistory.add(deleteCommand);
        deleteCommand.execute();
    }

    public void redo() {
        System.out.println("Redoing paste");
        // Get pasted shapes.
        List<IShape> pastedShapes = this.getShapes();
        // Remove pasted shapes.
        DeleteCommand deleteCommand = new DeleteCommand(paintCanvas, pastedShapes);
        CommandHistory.add(deleteCommand);
        deleteCommand.execute();
    }

}
