package controller;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.ShapeFactory;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;

// Command Pattern
class DrawShape implements ICommand {
    private IApplicationState applicationState;
    private PaintCanvasBase paintCanvas;
    static final private String commandName = "Draw";

    DrawShape(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        this.applicationState = applicationState;
        this.paintCanvas = paintCanvas;
    }

    public void execute() {
        if (Math.abs(applicationState.getEndPoint().getY() - applicationState.getStartPoint().getY()) > 10) {
            switch (applicationState.getActiveShapeType()) {
                case RECTANGLE:
                    IShape rectangle = ShapeFactory.createRectangle(applicationState, paintCanvas);
                    ShapeList.add(rectangle);
                    break;

                case ELLIPSE:
                    IShape ellipse = ShapeFactory.createEllipse(applicationState, paintCanvas);
                    ShapeList.add(ellipse);
                    break;

                case TRIANGLE:
                    IShape triangle = ShapeFactory.createTriangle(applicationState, paintCanvas);
                    ShapeList.add(triangle);
                    break;
            }
        }
    }
}
