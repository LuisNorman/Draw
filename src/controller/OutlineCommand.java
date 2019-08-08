package controller;

import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class OutlineCommand implements ICommand {
    private final PaintCanvasBase paintCanvas;

    public OutlineCommand(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void execute() {
        List<IShape> selectedShapes = SelectedShapes.getAll();
        for (IShape shape : selectedShapes) {
            String shapeName = shape.getShapeName();
            Location location = shape.getLocation();
            Point startPoint = location.getStartPoint();
            Point endPoint = location.getEndPoint();

            switch(shapeName) {
                case "Triangle" :
                    outlineTriangle(paintCanvas, startPoint, endPoint, shape);
                    break;

                case "Rectangle" :
                    outlineRectangle(paintCanvas, startPoint, endPoint, shape);
                    break;

                case "Ellipse" :
                    outlineEllipse(paintCanvas, startPoint, endPoint, shape);
                    break;
            }
        }
    }

    private static void outlineRectangle(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getSecondaryColor());
        graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
    }

    private static void outlineEllipse(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getSecondaryColor());
        graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
    }

    private static void outlineTriangle(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        int n = 3;
        int[] x = new int[]{startPoint.getX(), startPoint.getX()+(endPoint.getX()-startPoint.getX()), startPoint.getX()-(endPoint.getX()-startPoint.getX())};
        int[] y = new int[]{startPoint.getY(), startPoint.getY()+(endPoint.getY()-startPoint.getY()), startPoint.getY()+(endPoint.getY()-startPoint.getY())};
        graphics2d.drawPolygon(x, y, n);
    }

}
