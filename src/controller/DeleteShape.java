package controller;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.List;
import model.interfaces.IShape;
import model.persistence.Location;
import model.persistence.SelectedShapes;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

public class DeleteShape implements ICommand {
    private List<IShape> selectedShapes;
    private PaintCanvasBase paintCanvas;

    public DeleteShape(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
        selectedShapes = SelectedShapes.getAll();
    }

    @Override
    public void execute() {
        for (IShape shape : selectedShapes) {
            String shapeName = shape.getShapeName();
            Location location = shape.getLocation();
            Point startPoint = location.getStartPoint();
            Point endPoint = location.getEndPoint();
            switch(shapeName) {
                case "Triangle" :
                    removeTriangle(paintCanvas, startPoint, endPoint);
                    break;

                case "Rectangle" :
                    removeRectangle(paintCanvas, startPoint, endPoint);
                    break;

                case "Ellipse" :
                    removeEllipse(paintCanvas, startPoint, endPoint);
                    break;
            }
            ShapeList.remove(shape);
        }
    }

    private static void removeRectangle(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
        graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
    }

    private static void removeEllipse(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));

    }

    private static void removeTriangle(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        int[] x = new int[]{startPoint.getX(), endPoint.getX(), startPoint.getX() + (startPoint.getX() - endPoint.getX())};
        int[] y = new int[]{startPoint.getY(), endPoint.getY(), endPoint.getY()};
        int n = 3;
        graphics2d.fillPolygon(x, y, n);
        graphics2d.drawPolygon(x, y, n);
    }

}
