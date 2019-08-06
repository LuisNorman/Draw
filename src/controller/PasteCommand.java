package controller;

import model.persistence.Triangle;
import model.persistence.Ellipse;
import model.ShapeShadingType;
import model.interfaces.IShape;
import model.persistence.Location;
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
            String shapeName = shape.getShapeName();
            Location location = shape.getLocation();
            Point startPoint = location.getStartPoint();
            Point endPoint = location.getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, width*2+5);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);
            switch(shapeName) {
                case "Triangle" :
                    newShape = new Triangle(shape);
                    recreateTriangle(paintCanvas, newStartPoint, newEndPoint, shape);
                    break;

                case "Rectangle" :
                    newShape = new Rectangle(shape);
                    recreateRectangle(paintCanvas, newStartPoint, newEndPoint, shape);
                    break;

                case "Ellipse" :
                    newShape = new Ellipse(shape);
                    recreateEllipse(paintCanvas, newStartPoint, newEndPoint, shape);
                    break;
            }
            // im only updating the shape, i need to create a new one
            shape.setLocation(newStartPoint, newEndPoint);
            if (newShape != null) {
                newShape.setLocation(newStartPoint, newEndPoint);
                ShapeList.add(newShape);
            }
            else {
                throw new NullPointerException("There are no shapes to paste.");
            }
        }
    }

    private static void recreateRectangle(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
            graphics2d.setColor(shape.getSecondaryColor());
            graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.FILLED_IN) {
            graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE) {
            graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
        }
    }

    private static void recreateEllipse(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
            graphics2d.setColor(shape.getSecondaryColor());
            graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.FILLED_IN) {
            graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE) {
            graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
        }
    }

    private static void recreateTriangle(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        int n = 3;
        int[] x = new int[]{startPoint.getX(), startPoint.getX()+(endPoint.getX()-startPoint.getX()), startPoint.getX()-(endPoint.getX()-startPoint.getX())};
        int[] y = new int[]{startPoint.getY(), startPoint.getY()+(endPoint.getY()-startPoint.getY()), startPoint.getY()+(endPoint.getY()-startPoint.getY())};
        if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fillPolygon(x, y, n);
            graphics2d.setColor(shape.getSecondaryColor());
            graphics2d.drawPolygon(x, y, n);
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.FILLED_IN) {
            graphics2d.fillPolygon(x, y, n);
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE) {
            graphics2d.drawPolygon(x, y, n);
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
