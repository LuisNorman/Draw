package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public class Outliner {
    PaintCanvasBase paintCanvas;
    Point startPoint;
    Point endPoint;
    IShape shape;

    public Outliner(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape) {
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.shape = shape;
    }

    public void outline(IOutlineStrategy iOutlineStrategy) {
        iOutlineStrategy.outline(paintCanvas, startPoint, endPoint, shape);
    }
}
