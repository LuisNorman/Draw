package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class RemoveEllipseStrategy implements IRemoveStrategy {
    @Override
    public void remove(PaintCanvasBase paintCanvas, IShape shape) {
        Point startPoint = shape.getLocation().getStartPoint();
        Point endPoint = shape.getLocation().getEndPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));

    }
}
