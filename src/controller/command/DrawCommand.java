package controller.command;

import controller.*;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;
import java.util.LinkedList;
import java.util.List;

// Command Pattern
public class DrawCommand implements ICommand, IUndoRedoCommand {
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
                Shape shape = new Shape(paintCanvas, currentShape);
                IShapeStrategy iShapeStrategy = null;

                switch(currentShape.getShapeType()) {
                    case TRIANGLE :
                        iShapeStrategy = new TriangleStrategy();
                        break;

                    case RECTANGLE :
                        iShapeStrategy = new RectangleStrategy();
                        break;

                    case ELLIPSE :
                        iShapeStrategy = new EllipseStrategy();
                        break;
                }
                shape.recreate(iShapeStrategy);
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

    public void undo() {
        System.out.println("Undoing draw");
        List<IShape> shapes = this.getDrawnShapes();
        DeleteCommand deleteCommand = new DeleteCommand(paintCanvas, shapes);
        UndoCommandHistory.add(deleteCommand);
        deleteCommand.execute();
    }

    public void redo() {
        System.out.println("Redoing draw");
        // Add this (DrawCommand) to Redo Command History
//        DrawCommand mostRecentDrawCommand = (DrawCommand)lastCommand;
        List<IShape> shapes = this.getDrawnShapes();
        DeleteCommand deleteCommand = new DeleteCommand(paintCanvas, shapes);
        CommandHistory.add(deleteCommand);
        deleteCommand.execute();
    }




}
