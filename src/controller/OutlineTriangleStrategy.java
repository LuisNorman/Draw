package controller;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.TransformColor;
import view.interfaces.PaintCanvasBase;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class OutlineTriangleStrategy implements IOutlineStrategy {
    private final TransformColor transformColor = new TransformColor();

    @Override
    public void outline(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape, IApplicationState applicationState) {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{9}, 0);
        graphics2d.setStroke(stroke);
        graphics2d.setColor(transformColor.transform(applicationState.getActiveSecondaryColor()));
        int n = 3;
        int[] x = new int[]{startPoint.getX(), startPoint.getX()+(endPoint.getX()-startPoint.getX()), startPoint.getX()-(endPoint.getX()-startPoint.getX())};
        int[] y = new int[]{startPoint.getY(), startPoint.getY()+(endPoint.getY()-startPoint.getY()), startPoint.getY()+(endPoint.getY()-startPoint.getY())};
        graphics2d.drawPolygon(x, y, n);
    }
}
