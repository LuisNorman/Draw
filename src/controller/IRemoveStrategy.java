package controller;

import view.interfaces.PaintCanvasBase;

public interface IRemoveStrategy {
    void remove(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint);
}
