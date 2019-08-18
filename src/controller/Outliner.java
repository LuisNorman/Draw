package controller;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public class Outliner {
    PaintCanvasBase paintCanvas;
    Point startPoint;
    Point endPoint;
    IShape shape;
    IApplicationState applicationState;

    public Outliner(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape, IApplicationState applicationState) {
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.shape = shape;
        this.applicationState = applicationState;
    }

    public void outline(IOutlineStrategy iOutlineStrategy) {
        iOutlineStrategy.outline(paintCanvas, startPoint, endPoint, shape, applicationState);
    }
}
