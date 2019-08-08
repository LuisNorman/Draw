package controller;

import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class OutlineCommand implements ICommand {
    private final PaintCanvasBase paintCanvas;

    public OutlineCommand(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void execute() {
        List<IShape> selectedShapes = SelectedShapes.getAll();
        for (IShape shape : selectedShapes) {
            Location location = shape.getLocation();
            Point startPoint = location.getStartPoint();
            Point endPoint = location.getEndPoint();
            Outliner outliner = new Outliner(paintCanvas, startPoint, endPoint, shape);
            IOutlineStrategy iOutlineStrategy = null;
            switch(shape.getShapeType()) {
                case TRIANGLE :
                    iOutlineStrategy = new OutlineTriangleStrategy();
                    break;

                case RECTANGLE :
                    iOutlineStrategy = new OutlineRectangleStrategy();
                    break;

                case ELLIPSE :
                    iOutlineStrategy = new OutlineEllipseStrategy();
                    break;
            }
            outliner.outline(iOutlineStrategy);
        }
    }



}
