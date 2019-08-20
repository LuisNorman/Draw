package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public class Recreator {
    PaintCanvasBase paintCanvas;
    IShape shape;

    public Recreator(PaintCanvasBase paintCanvas, IShape shape) {
        this.paintCanvas = paintCanvas;
        this.shape = shape;
    }

    public void recreate(IRecreateStrategy iRecreateStrategy) {
        iRecreateStrategy.recreate(paintCanvas, shape);
    }
}
