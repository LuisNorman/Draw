package controller.command;

import controller.*;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.ShapeFactory;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

import java.util.LinkedList;
import java.util.List;

// Command Pattern
public class DrawCommand implements ICommand {
    private IApplicationState applicationState;
    private PaintCanvasBase paintCanvas;
    static final private String commandName = "Draw";
    private IShape shape;
    private List<IShape> shapes;
    private List<IShape> drawnShapes;

    public DrawCommand(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        this.applicationState = applicationState;
        this.paintCanvas = paintCanvas;
        drawnShapes = new LinkedList<>();
    }

    public DrawCommand(PaintCanvasBase paintCanvas, List<IShape> shapes) {
        this.shapes = shapes;
        this.paintCanvas = paintCanvas;
        drawnShapes = new LinkedList<>();
    }

    public void execute() {
        // Check if we're drawing shapes for the first time
        if (shapes == null) {
            applicationState.setHeight();
            applicationState.setWidth();
            if (Math.abs(applicationState.getEndPoint().getY() - applicationState.getStartPoint().getY()) > 10) {
                switch (applicationState.getActiveShapeType()) {
                    case RECTANGLE:
                        shape = ShapeFactory.createRectangle(applicationState, paintCanvas);
                        ShapeList.add(shape);
                        break;

                    case ELLIPSE:
                        shape = ShapeFactory.createEllipse(applicationState, paintCanvas);
                        ShapeList.add(shape);
                        break;

                    case TRIANGLE:
                        shape = ShapeFactory.createTriangle(applicationState, paintCanvas);
                        ShapeList.add(shape);
                        break;
                }
                addShapesToList(shape);
            }
        }
        // Check if we're drawing previous shapes
        else {
            for(IShape currentShape: shapes) {
                Recreator recreator = new Recreator(paintCanvas, currentShape);
                IRecreateStrategy iRecreateStrategy = null;
                switch(currentShape.getShapeType()) {
                    case TRIANGLE :
                        iRecreateStrategy = new RecreateTriangleStrategy();
                        break;

                    case RECTANGLE :
                        iRecreateStrategy = new RecreateRectangleStrategy();
                        break;

                    case ELLIPSE :
                        iRecreateStrategy = new RecreateEllipseStrategy();
                        break;
                    }
                    recreator.recreate(iRecreateStrategy);
                    ShapeList.add(currentShape);
                    addShapesToList(currentShape);
            }
        }
    }

    private void addShapesToList(IShape shape) {
        drawnShapes.add(shape);
    }

    List<IShape> getDrawnShapes() {
        return drawnShapes;
    }

    public IShape getShape() {
        return shape;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
