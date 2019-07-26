package model.persistence;

import model.interfaces.IShape;
import controller.Point;
import java.util.List;

public class SelectedShape {
    static IShape selectedShape;

    public static void set(Point targetPoint) {
        List<IShape> shapeList = ShapeList.getShapeList();
        for (IShape currentShape : shapeList) {
            String currentShapeName = currentShape.getShapeName();
            Location currentShapeLocation = currentShape.getLocation();
            Point currentShapeStartPoint = currentShapeLocation.getStartPoint();
            Point currentShapeEndPoint = currentShapeLocation.getEndPoint();

            if (currentShapeName.equals("Triangle")) {
                if ((currentShapeStartPoint.getX() + (currentShapeStartPoint.getX() - currentShapeEndPoint.getX()) <= targetPoint.getX()) && (targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
                    selectedShape = currentShape;
                }
            }
            else if (currentShapeName.equals("Rectangle") || currentShapeName.equals("Ellipse")){
                if ((currentShapeStartPoint.getX() <= targetPoint.getX() && targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
                    selectedShape = currentShape;
                }
            }
        }
    }

    public static void set(IShape shape) {
        if (shape != null) {
            selectedShape = shape;
        }

    }

    public static IShape get() {
        return selectedShape;
    }
}
