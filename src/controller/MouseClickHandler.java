package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.interfaces.IApplicationState;
import model.persistence.SelectedShape;
import view.interfaces.PaintCanvasBase;

public class MouseClickHandler extends MouseAdapter {
    private  IApplicationState applicationState;
    private PaintCanvasBase paintCanvas;

    public MouseClickHandler(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        this.applicationState = applicationState;
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point startPoint = new Point(e.getX(), e.getY());
        applicationState.setStartPoint(startPoint);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ICommand command = new NullCommand();
        Point endPoint = new Point(e.getX(), e.getY());
        applicationState.setEndPoint(endPoint);
        switch (applicationState.getActiveStartAndEndPointMode()) {
            case DRAW:
                command = new DrawCommand(applicationState, paintCanvas);
                break;

            case MOVE:
                SelectedShape.set(applicationState.getStartPoint()); // Move from current selected shape
                command = new MoveCommand(paintCanvas, endPoint);
                break;

            case SELECT:
                command = new SelectCommand(applicationState.getStartPoint(), endPoint);
                break;
        }
        command.execute();
        CommandHistory.add(command);
    }
}

