package controller;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.Point;
import view.interfaces.PaintCanvasBase;

public interface IShapeStrategy {
    void recreate(PaintCanvasBase paintCanvas, IShape shape);
    void remove(PaintCanvasBase paintCanvas, IShape shape);
    void outline(PaintCanvasBase paintCanvas, IShape shape, IApplicationState applicationState);
}
