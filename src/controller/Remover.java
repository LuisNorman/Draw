package controller;

import view.interfaces.PaintCanvasBase;

public class Remover {
    PaintCanvasBase paintCanvas;
    Point startPoint;
    Point endPoint;

    public Remover(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint) {
         this.paintCanvas = paintCanvas;
         this.startPoint = startPoint;
         this.endPoint = endPoint;
    }
    public void remove(IRemoveStrategy removeStrategy) {
        removeStrategy.remove(paintCanvas, startPoint, endPoint);
    }
}
