package model.persistence;

import model.ShapeType;
import model.interfaces.IShape;

import java.util.List;

public class SelectedShape {
    private static IShape selectedShape;

    public static void set(Point targetPoint) {
        List<IShape> shapeList = ShapeList.getShapeList();
        for (IShape currentShape : shapeList) {
            Location currentShapeLocation = currentShape.getLocation();
            Point currentShapeStartPoint = currentShapeLocation.getStartPoint();
            Point currentShapeEndPoint = currentShapeLocation.getEndPoint();

            if (currentShape.getShapeType() == ShapeType.TRIANGLE) {
                if ((currentShapeStartPoint.getX() + (currentShapeStartPoint.getX() - currentShapeEndPoint.getX()) <= targetPoint.getX()) && (targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
                    selectedShape = currentShape;
                }
            }
            else if (currentShape.getShapeType() == ShapeType.ELLIPSE || currentShape.getShapeType() == ShapeType.RECTANGLE){
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
