package controller;

import model.ShapeShadingType;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;
import java.awt.Graphics2D;

public class RecreateRectangleStrategy implements IRecreateStrategy {
    @Override
    public void recreate(PaintCanvasBase paintCanvas, IShape shape) {
        Point startPoint = shape.getLocation().getStartPoint();
        Point endPoint = shape.getLocation().getEndPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(shape.getPrimaryColor());
        if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
            graphics2d.setColor(shape.getSecondaryColor());
            graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.FILLED_IN) {
            graphics2d.fillRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
        }
        else if (shape.getShapeShadingType() == ShapeShadingType.OUTLINE) {
            graphics2d.drawRect(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(),endPoint.getY() - startPoint.getY());
        }
    }
}
