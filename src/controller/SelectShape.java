package controller;

import model.interfaces.ILocation;
import model.interfaces.IShape;
import model.persistence.ShapeList;

import java.util.LinkedList;
import java.util.List;

class SelectShape implements ICommand {
    private static List<IShape> selectedShapes = new LinkedList<>();
    private static IShape selectedShape;

    static void select(Point targetPoint) {
        boolean found = false;
        boolean shapeSelectedAlready = false;
        List<IShape> shapeList = ShapeList.getShapeList();
        List<IShape> selectedShapes = getSelectedShapes();
        // Get all shapes and loop to find which one intersects with
        // the target point and then add it to the selected shapes list.
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
            System.out.println("Could not find shape at current location.");
        }
    }

    static void clearSelectedShapes() {
        selectedShapes = new LinkedList<>();
    }

    static void updateSelectedShape(Point targetPoint) {
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

    static List<IShape> getSelectedShapes() {
        return selectedShapes;
    }

    static IShape getSelectedShape() {
        return selectedShape;
    }
}
