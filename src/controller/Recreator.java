package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public class Recreator {
    PaintCanvasBase paintCanvas;
    Point startPoint;
    Point endPoint;
    IShape shape;

    public Recreator(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.shape = shape;
    }

    public void recreate(IRecreateStrategy iRecreateStrategy) {
        iRecreateStrategy.recreate(paintCanvas, startPoint, endPoint, shape);
    }
}
