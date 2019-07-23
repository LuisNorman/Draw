package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.StartAndEndPointMode;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;


public class MouseClickHandler extends MouseAdapter {
    private  IApplicationState applicationState;
    private PaintCanvasBase paintCanvas;
    private List<IShape> selectedShapes;

    public MouseClickHandler(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
        this.applicationState = applicationState;
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point startPoint = new Point(e.getX(), e.getY());
        applicationState.setStartPoint(startPoint);
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.SELECT) {
            selectedShapes = ShapeSelector.select(startPoint);
        }
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.MOVE) {
            ShapeSelector.updateSelectedShape(startPoint);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point endPoint = new Point(e.getX(), e.getY());
        applicationState.setEndPoint(endPoint);
        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.DRAW) {
            applicationState.setWidth();
            applicationState.setHeight();
            DrawShape.draw(applicationState, paintCanvas);
        }

        if (applicationState.getActiveStartAndEndPointMode() == StartAndEndPointMode.MOVE) {
            if (selectedShapes == null || selectedShapes.size() == 0) {
                System.out.println("No shapes selected");
            }
            else {
                MoveShape.move(paintCanvas, endPoint);
            }

        }
    }
}

