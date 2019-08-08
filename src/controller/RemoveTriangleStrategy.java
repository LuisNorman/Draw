package controller;

import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class RemoveTriangleStrategy implements IRemoveStrategy {
    @Override
    public void remove(PaintCanvasBase paintCanvas, controller.Point startPoint, Point endPoint) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        int[] x = new int[]{startPoint.getX(), endPoint.getX(), startPoint.getX() + (startPoint.getX() - endPoint.getX())};
        int[] y = new int[]{startPoint.getY(), endPoint.getY(), endPoint.getY()};
        int n = 3;
        graphics2d.fillPolygon(x, y, n);
        graphics2d.drawPolygon(x, y, n);
    }
}
