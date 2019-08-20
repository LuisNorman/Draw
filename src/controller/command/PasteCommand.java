package controller.command;

import controller.*;
import model.persistence.Triangle;
import model.persistence.Ellipse;
import model.interfaces.IShape;
import model.persistence.Rectangle;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

import java.util.LinkedList;
import java.util.List;

public class PasteCommand implements ICommand {
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
        for (IShape shape : shapesToPaste) {
            IShape newShape = null;
            Point startPoint = shape.getLocation().getStartPoint();
            Point endPoint = shape.getLocation().getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, width*2+5);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);
            shape.setLocation(newStartPoint, newEndPoint);
            Recreator recreator = new Recreator(paintCanvas, shape);
            IRecreateStrategy iRecreateStrategy = null;

            switch(shape.getShapeType()) {
                case TRIANGLE :
                    newShape = new Triangle(shape);
                    iRecreateStrategy = new RecreateTriangleStrategy();
                    break;

                case RECTANGLE :
                    newShape = new Rectangle(shape);
                    iRecreateStrategy = new RecreateRectangleStrategy();
                    break;

                case ELLIPSE :
                    newShape = new Ellipse(shape);
                    iRecreateStrategy = new RecreateEllipseStrategy();
                    break;
            }
            recreator.recreate(iRecreateStrategy);

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


}
