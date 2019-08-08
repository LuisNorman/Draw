package controller;

import view.interfaces.PaintCanvasBase;
import java.awt.Graphics2D;
import java.awt.Color;

public class RemoveRectangleStrategy implements IRemoveStrategy {
    @Override
    public void remove(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint){
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
        graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY());
    }
}
