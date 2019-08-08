package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class OutlineRectangleStrategy implements IOutlineStrategy {
    @Override
    public void outline(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getSecondaryColor());
        graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
    }
}
