package controller;

import java.util.List;
import model.ShapeShadingType;
import model.persistence.Location;
import model.interfaces.IShape;
import model.persistence.SelectedShape;
import model.persistence.SelectedShapes;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class MoveShape implements ICommand{

    static void move(PaintCanvasBase paintCanvasBase, Point newPoint) {
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
        for (IShape targetShape : selectedShapes) {
            String shapeName = targetShape.getShapeName();
            Location location = targetShape.getLocation();
            Point startPoint = location.getStartPoint();
            Point endPoint = location.getEndPoint();
            int width = endPoint.getX()-startPoint.getX();
            int height = endPoint.getY()-startPoint.getY();
            Point newStartPoint = getNewStartPoint(startPoint, deltaX, deltaY);
            Point newEndPoint = getNewEndPoint(newStartPoint, width, height);

            System.out.println("Original start point: "+startPoint.getX()+", "+startPoint.getY());
            System.out.println("Original end point: "+endPoint.getX()+", "+endPoint.getY());
            System.out.println("New start point: "+newStartPoint.getX()+", "+newStartPoint.getY());
            System.out.println("New end point: " +newEndPoint.getX()+", "+newEndPoint.getY());

            // Remove and recreate the shape.
            switch(shapeName) {
                case "Triangle" :
                    removeTriangle(paintCanvasBase, startPoint, endPoint);
                    recreateTriangle(paintCanvasBase, newStartPoint, newEndPoint, targetShape, newPoint);
                    break;

                case "Rectangle" :
                    removeRectangle(paintCanvasBase, startPoint, endPoint);
                    recreateRectangle(paintCanvasBase, newStartPoint, newEndPoint, targetShape, newPoint);
                    break;

                case "Ellipse" :
                    removeEllipse(paintCanvasBase, startPoint, endPoint);
                    recreateEllipse(paintCanvasBase, newStartPoint, newEndPoint, targetShape, newPoint);
                    break;
            }
            ShapeList.updateStartPoint(targetShape, newStartPoint);
            ShapeList.updateEndPoint(targetShape, newEndPoint);
        }
    }

    /* These are helper functions to remove & rebuild the shape. */

    private static void removeRectangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
        graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
    }

    private static void removeEllipse(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));

    }

    private static void removeTriangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        int[] x = new int[]{startPoint.getX(), endPoint.getX(), startPoint.getX() + (startPoint.getX() - endPoint.getX())};
        int[] y = new int[]{startPoint.getY(), endPoint.getY(), endPoint.getY()};
        int n = 3;
        graphics2d.fillPolygon(x, y, n);
        graphics2d.drawPolygon(x, y, n);
    }

    private static void recreateRectangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint, IShape shape, Point newPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
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

    private static void recreateEllipse(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint, IShape shape, Point newPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
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

    private static void recreateTriangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint, IShape shape, Point newPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
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

    private static Point getNewStartPoint(Point startPoint, int deltaX, int deltaY) {
        System.out.println("deltaX: "+deltaX);
        System.out.println("deltaY: "+deltaY);
        System.out.println("startPointY: "+ startPoint.getY());
        int newStartPoint_X = startPoint.getX()+deltaX;
        int newStartPoint_Y = startPoint.getY()+deltaY;
        return new Point(newStartPoint_X, newStartPoint_Y);
    }

    private static  Point getNewEndPoint(Point newStartPoint, int width, int height) {
        int newEndPoint_X = newStartPoint.getX()+width;
        int newEndPoint_Y = newStartPoint.getY()+height;
        return new Point(newEndPoint_X, newEndPoint_Y);
    }

}
