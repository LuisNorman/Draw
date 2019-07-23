package controller;

import model.ShapeShadingType;
import model.interfaces.ILocation;
import model.interfaces.IShape;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.awt.geom.Ellipse2D;

class MoveShape {

    static void move(PaintCanvasBase paintCanvasBase, IShape targetShape, Point newPoint) {
        System.out.println("Moving "+targetShape.getShapeName()+" in position "+targetShape.getLocation().getStartPoint().getX() +", "+targetShape.getLocation().getStartPoint().getY());
        String shapeName = targetShape.getShapeName();
        ILocation location = targetShape.getLocation();
        Point startPoint = location.getStartPoint();
        Point endPoint = location.getEndPoint();
        switch(shapeName) {
            case "Triangle" :
                removeTriangle(paintCanvasBase, startPoint, endPoint);
                recreateTriangle(paintCanvasBase, startPoint, endPoint, targetShape, newPoint);
                break;

            case "Rectangle" :
                removeRectangle(paintCanvasBase, startPoint, endPoint);
                recreateRectangle(paintCanvasBase, startPoint, endPoint, targetShape, newPoint);
                break;

            case "Ellipse" :
                removeEllipse(paintCanvasBase, startPoint, endPoint);
                recreateEllipse(paintCanvasBase, startPoint, endPoint, targetShape, newPoint);
                break;
        }
        Point newEndPoint = new Point(newPoint.getX()+(endPoint.getX()-startPoint.getX()), newPoint.getY()+(endPoint.getY()-startPoint.getY()));
        ShapeList.updateStartPoint(targetShape, newPoint);
        ShapeList.updateEndPoint(targetShape, newEndPoint);
        ShapeSelector.clearSelectedShape();
    }

    static void removeRectangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
        graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
    }

    static void removeEllipse(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));

    }

    static void removeTriangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        int[] x = new int[]{startPoint.getX(), endPoint.getX(), startPoint.getX() + (startPoint.getX() - endPoint.getX())};
        int[] y = new int[]{startPoint.getY(), endPoint.getY(), endPoint.getY()};
        int n = 3;
        graphics2d.fillPolygon(x, y, n);
        graphics2d.drawPolygon(x, y, n);
    }

    static void recreateRectangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint, IShape shape, Point newPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fillRect(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
            graphics2d.setColor(shape.getSecondaryColor());
            graphics2d.drawRect(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.FILLED_IN) {
            graphics2d.fillRect(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE) {
            graphics2d.drawRect(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
        }
    }

    static void recreateEllipse(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint, IShape shape, Point newPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fill(new Ellipse2D.Double(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
            graphics2d.setColor(shape.getSecondaryColor());
            graphics2d.draw(new Ellipse2D.Double(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.FILLED_IN) {
            graphics2d.fill(new Ellipse2D.Double(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE) {
            graphics2d.draw(new Ellipse2D.Double(newPoint.getX(), newPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        }
    }

    static void recreateTriangle(PaintCanvasBase paintCanvasBase, Point startPoint, Point endPoint, IShape shape, Point newPoint) {
        Graphics2D graphics2d = paintCanvasBase.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        int n = 3;
        int[] x = new int[]{newPoint.getX(), newPoint.getX()+(endPoint.getX()-startPoint.getX()), newPoint.getX()-(endPoint.getX()-startPoint.getX())};
        int[] y = new int[]{newPoint.getY(), newPoint.getY()+(endPoint.getY()-startPoint.getY()), newPoint.getY()+(endPoint.getY()-startPoint.getY())};
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

}
