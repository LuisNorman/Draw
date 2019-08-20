package controller.command;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.ShapeFactory;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

import java.util.List;

// Command Pattern
public class DrawCommand implements ICommand {
    private IApplicationState applicationState;
    private PaintCanvasBase paintCanvas;
    static final private String commandName = "Draw";
    private IShape shape;

    public DrawCommand(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        this.applicationState = applicationState;
        this.paintCanvas = paintCanvas;
    }

    public DrawCommand(PaintCanvasBase paintCanvas, List<IShape> shapes) {

    }

    public void execute() {
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
        }
    }

    public IShape getShape() {
        return shape;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
