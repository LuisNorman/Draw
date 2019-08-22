package controller;

import model.ShapeShadingType;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.Point;
import model.persistence.TransformColor;
import view.interfaces.PaintCanvasBase;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class EllipseStrategy implements IShapeStrategy {
    private final TransformColor transformColor = new TransformColor();

    @Override
    public void remove(PaintCanvasBase paintCanvas, IShape shape) {
        model.persistence.Point startPoint = shape.getLocation().getStartPoint();
        model.persistence.Point endPoint = shape.getLocation().getEndPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));
        graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY() - startPoint.getY()));

    }

    @Override
    public void recreate(PaintCanvasBase paintCanvas, IShape shape) {
        model.persistence.Point startPoint = shape.getLocation().getStartPoint();
        model.persistence.Point endPoint = shape.getLocation().getEndPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
            graphics2d.setColor(shape.getSecondaryColor());
            graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.FILLED_IN) {
            graphics2d.fill(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE) {
            graphics2d.draw(new Ellipse2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY()));
        }
    }

    @Override
    public void outline(PaintCanvasBase paintCanvas, IShape shape, IApplicationState applicationState) {
        model.persistence.Point startPoint = shape.getLocation().getStartPoint();
        model.persistence.Point endPoint = shape.getLocation().getEndPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{9}, 0);
        graphics2d.setStroke(stroke);
        graphics2d.setColor(transformColor.transform(applicationState.getActivePrimaryColor()));
        graphics2d.draw(new Ellipse2D.Double(startPoint.getX()-5, startPoint.getY()-5, endPoint.getX()-startPoint.getX()+10,endPoint.getY() - startPoint.getY()+10));
    }

}
