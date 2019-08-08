package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.StartAndEndPointMode;
import model.interfaces.IApplicationState;
import model.persistence.SelectedShape;
import view.gui.PaintCanvas;
import view.interfaces.PaintCanvasBase;


public class MouseClickHandler extends MouseAdapter {
    private  IApplicationState applicationState;
    private PaintCanvasBase paintCanvas;
    private ICommand command;

    public MouseClickHandler(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        this.applicationState = applicationState;
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point startPoint = new Point(e.getX(), e.getY());
        applicationState.setStartPoint(startPoint);
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.SELECT) {
            command = new SelectShape(startPoint);
            command.execute();
        }
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.MOVE) {
            SelectedShape.set(startPoint);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        command = null;
        Point endPoint = new Point(e.getX(), e.getY());
        applicationState.setEndPoint(endPoint);
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.DRAW) {
            command = new DrawShape(applicationState, paintCanvas);
        }

        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.MOVE) {
            command = new MoveShape(paintCanvas, endPoint);
        }
        if(command != null) {
            command.execute();
        }
    }
}

