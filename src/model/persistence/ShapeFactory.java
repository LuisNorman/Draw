package model.persistence;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public class ShapeFactory {

    public static IShape createRectangle(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        return new Rectangle(applicationState, paintCanvas);
    }

    public static IShape createEllipse(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        return new Ellipse(applicationState, paintCanvas);
    }

    public static IShape createTriangle(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        return new Triangle(applicationState, paintCanvas);
    }
}
