package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.StartAndEndPointMode;
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
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.SELECT) {
            ICommand select = new SelectShape(startPoint);
            select.execute();
        }
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.MOVE) {
            SelectedShape.set(startPoint);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point endPoint = new Point(e.getX(), e.getY());
        applicationState.setEndPoint(endPoint);
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.DRAW) {
            applicationState.setWidth();
            applicationState.setHeight();
            ICommand draw = new DrawShape(applicationState, paintCanvas);
            draw.execute();
        }

        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.MOVE) {
            ICommand move = new MoveShape(paintCanvas, endPoint);
            move.execute();
        }
    }
}

