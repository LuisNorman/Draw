package controller;

import model.interfaces.ILocation;
import model.interfaces.IShape;
import model.persistence.ShapeList;

import java.util.LinkedList;
import java.util.List;

public class ShapeSelector {
    private static List<IShape> selectedShapes = new LinkedList<>();
    private static IShape selectedShape;

    public static List<IShape> select(Point targetPoint) {
        boolean found = false;
        boolean shapeSelectedAlready = false;
        List<IShape> shapeList = ShapeList.getShapeList();
        List<IShape> selectedShapes = getSelectedShapes();
        for (IShape currentShape : shapeList) {
            String currentShapeName = currentShape.getShapeName();
            ILocation currentShapeLocation = currentShape.getLocation();
            Point currentShapeStartPoint = currentShapeLocation.getStartPoint();
            Point currentShapeEndPoint = currentShapeLocation.getEndPoint();

            if (currentShapeName.equals("Triangle")) {
                if ((currentShapeStartPoint.getX() + (currentShapeStartPoint.getX() - currentShapeEndPoint.getX()) <= targetPoint.getX()) && (targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
                    if (selectedShapes.contains(currentShape)){
                        shapeSelectedAlready = true;
                        System.out.println("Shape selected already.");
                    }
                    else {
                        selectedShape = currentShape;
                        selectedShapes.add(currentShape);
                        found = true;
                        System.out.println(currentShapeName+" selected.");
                        break;
                    }
                }
            }
            else if (currentShapeName.equals("Rectangle") || currentShapeName.equals("Ellipse")){
                if ((currentShapeStartPoint.getX() <= targetPoint.getX() && targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
                    if (selectedShapes.contains(currentShape)){
                        shapeSelectedAlready = true;
                        System.out.println("Shape selected already.");
                    }
                    else {
                        selectedShape = currentShape;
                        selectedShapes.add(currentShape);
                        found = true;
                        System.out.println(currentShapeName+" selected.");
                        break;
                    }
                }
            }
        }
        if (!found && !shapeSelectedAlready) {
            System.out.println("No shape selected.");
        }
        return selectedShapes;
    }

    public static void clearSelectedShapes() {
        selectedShapes = new LinkedList<>();
    }

    public static void updateSelectedShape(Point targetPoint) {
        List<IShape> shapeList = ShapeList.getShapeList();
        for (IShape currentShape : shapeList) {
            String currentShapeName = currentShape.getShapeName();
            ILocation currentShapeLocation = currentShape.getLocation();
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

    public static List<IShape> getSelectedShapes() {
        return selectedShapes;
    }

    public static IShape getSelectedShape() {
        return selectedShape;
    }
}
