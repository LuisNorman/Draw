package controller;

import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public interface IRemoveStrategy {
    void remove(PaintCanvasBase paintCanvas, IShape shape);
}
