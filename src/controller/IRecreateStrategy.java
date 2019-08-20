package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public interface IRecreateStrategy {
    void recreate(PaintCanvasBase paintCanvas, IShape shape);
}
