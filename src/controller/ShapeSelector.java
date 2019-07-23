package controller;

import model.interfaces.ILocation;
import model.interfaces.IShape;
import model.persistence.ShapeList;

import java.util.List;

public class ShapeSelector {
    private static IShape selectedShape;

    public static IShape select(Point targetPoint) {
        List<IShape> shapeList = ShapeList.getShapeList();
        for (IShape currentShape : shapeList) {
            String currentShapeName = currentShape.getShapeName();
            ILocation currentShapeLocation = currentShape.getLocation();
            Point currentShapeStartPoint = currentShapeLocation.getStartPoint();
            Point currentShapeEndPoint = currentShapeLocation.getEndPoint();
            if (currentShapeName.equals("Triangle")) {
                if ((currentShapeStartPoint.getX() + (currentShapeStartPoint.getX() - currentShapeEndPoint.getX()) <= targetPoint.getX()) && (targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
                    selectedShape = currentShape;
                    System.out.println(currentShapeName+" selected!");
                }
            }
            else if (currentShapeName.equals("Rectangle") || currentShapeName.equals("Ellipse")){
                if ((currentShapeStartPoint.getX() <= targetPoint.getX() && targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
                    selectedShape = currentShape;
                    System.out.println(currentShapeName+" selected!");
                }
            }
        }
        return selectedShape;
    }

    public static void clearSelectedShape() {
        selectedShape = null;
    }

    public static IShape getSelectedShape() {
        return selectedShape;
    }
}
