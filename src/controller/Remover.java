package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public class Remover {
    private PaintCanvasBase paintCanvas;
    private IShape shape;

    public Remover(PaintCanvasBase paintCanvas, IShape shape) {
        this.shape = shape;
        this.paintCanvas = paintCanvas;
    }
    public void remove(IRemoveStrategy removeStrategy) {
        removeStrategy.remove(paintCanvas, shape);
    }

    public IShape getShape() {
        return shape;
    }
}
