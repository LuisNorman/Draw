package controller;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;

public interface IOutlineStrategy {
    void outline(PaintCanvasBase paintCanvas, Point startPoint, Point endPoint, IShape shape, IApplicationState applicationState);
}