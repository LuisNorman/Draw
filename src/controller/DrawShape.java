package controller;

import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.ShapeFactory;
import model.persistence.ShapeList;
import view.interfaces.PaintCanvasBase;


class DrawShape {
    static void draw(IApplicationState applicationState, PaintCanvasBase paintCanvas) {
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
