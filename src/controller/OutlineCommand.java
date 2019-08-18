package controller;

import model.ShapeShadingType;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;
import java.util.List;

public class OutlineCommand implements ICommand {
    private final PaintCanvasBase paintCanvas;
    private final IApplicationState applicationState;

    public OutlineCommand(PaintCanvasBase paintCanvas, IApplicationState applicationState) {
        this.paintCanvas = paintCanvas;
        this.applicationState = applicationState;
    }

    @Override
    public void execute() {
        List<IShape> selectedShapes = SelectedShapes.getAll();
        List<IShape> shapeList = ShapeList.getShapeList();
        for (IShape shape : selectedShapes) {
            IShape iShape = null;
            Point startPoint = shape.getLocation().getStartPoint();
            Point endPoint = shape.getLocation().getEndPoint();
            Outliner outliner = new Outliner(paintCanvas, startPoint, endPoint, shape, applicationState);
            IOutlineStrategy iOutlineStrategy = null;
            switch(shape.getShapeType()) {
                case TRIANGLE :
                    iOutlineStrategy = new OutlineTriangleStrategy();
                    if (!shapeList.contains(shape)) {
                        iShape = new Triangle(shape);
                    }
                    break;

                case RECTANGLE :
                    iOutlineStrategy = new OutlineRectangleStrategy();
                    if (!shapeList.contains(shape)) {
                        iShape = new Rectangle(shape);
                    }
                    break;

                case ELLIPSE :
                    iOutlineStrategy = new OutlineEllipseStrategy();
                    if (!shapeList.contains(shape)) {
                        iShape = new Ellipse(shape);
                    }
                    break;
            }
            if (!shapeList.contains(shape) && iShape != null) {
                iShape.setShapeShadingType(ShapeShadingType.OUTLINE);
                shapeList.add(iShape);
            }

            outliner.outline(iOutlineStrategy);
        }
    }



}
