package controller;

import model.ShapeShadingType;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.Point;
import model.persistence.TransformColor;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class TriangleStrategy implements IShapeStrategy {
    private final TransformColor transformColor = new TransformColor();

    @Override
    public void recreate(PaintCanvasBase paintCanvas, IShape shape) {
        model.persistence.Point startPoint = shape.getLocation().getStartPoint();
        model.persistence.Point endPoint = shape.getLocation().getEndPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
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

    @Override
    public void remove(PaintCanvasBase paintCanvas, IShape shape) {
        model.persistence.Point startPoint = shape.getLocation().getStartPoint();
        model.persistence.Point endPoint = shape.getLocation().getEndPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        int[] x = new int[]{startPoint.getX(), endPoint.getX(), startPoint.getX() + (startPoint.getX() - endPoint.getX())};
        int[] y = new int[]{startPoint.getY(), endPoint.getY(), endPoint.getY()};
        int n = 3;
        graphics2d.fillPolygon(x, y, n);
        graphics2d.drawPolygon(x, y, n);
    }

    @Override
    public void outline(PaintCanvasBase paintCanvas, IShape shape, model.persistence.Point startPoint, Point endPoint, IApplicationState applicationState) {
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
