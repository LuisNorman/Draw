package model.persistence;

import controller.IShapeStrategy;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public class Shape {
    private PaintCanvasBase paintCanvas;
    private IShape shape;
    private Point startPoint;
    private Point endPoint;
    private IApplicationState applicationState;

    public Shape(PaintCanvasBase paintCanvas, IShape shape) {
        this.paintCanvas = paintCanvas;
        this.shape = shape;
    }

    public Shape(PaintCanvasBase paintCanvas, IShape shape, Point startPoint, Point endPoint, IApplicationState applicationState) {
        this.paintCanvas = paintCanvas;
        this.shape = shape;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.applicationState = applicationState;
    }

    public void recreate(IShapeStrategy iShapeStrategy) {
        iShapeStrategy.recreate(paintCanvas, shape);
    }

    public void remove(IShapeStrategy iShapeStrategy) {
        iShapeStrategy.remove(paintCanvas, shape);
    }

    public void outline(IShapeStrategy iShapeStrategy) {
        iShapeStrategy.outline(paintCanvas, shape, applicationState);
    }

}
