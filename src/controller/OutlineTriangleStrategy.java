package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;
import java.awt.Graphics2D;

public class OutlineTriangleStrategy implements IOutlineStrategy {
    @Override
    public void outline(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        int n = 3;
        int[] x = new int[]{startPoint.getX(), startPoint.getX()+(endPoint.getX()-startPoint.getX()), startPoint.getX()-(endPoint.getX()-startPoint.getX())};
        int[] y = new int[]{startPoint.getY(), startPoint.getY()+(endPoint.getY()-startPoint.getY()), startPoint.getY()+(endPoint.getY()-startPoint.getY())};
        graphics2d.drawPolygon(x, y, n);
    }
}
