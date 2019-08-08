package controller;

import model.persistence.Triangle;
import model.persistence.Ellipse;
import model.ShapeShadingType;
import model.interfaces.IShape;
import model.persistence.Rectangle;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class PasteCommand implements ICommand {
    private final PaintCanvasBase paintCanvas;

    public PasteCommand(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void execute() {
        List<IShape> copiedShapes = CopyCommand.getCopiedShapes();

        for (IShape shape : copiedShapes) {
            IShape newShape = null;
            Point startPoint = shape.getLocation().getStartPoint();
            Point endPoint = shape.getLocation().getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, width*2+5);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);

            Recreator recreator = new Recreator(paintCanvas, newStartPoint, newEndPoint, shape);
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

            // im only updating the shape, i need to create a new one
            shape.setLocation(newStartPoint, newEndPoint);
            newShape.setLocation(newStartPoint, newEndPoint);
            ShapeList.add(newShape);

        }
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

}
