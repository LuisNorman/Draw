package controller;

import model.persistence.Location;
import model.interfaces.IShape;
import model.persistence.ShapeList;
import java.util.LinkedList;
import java.util.List;

class SelectShape {
    private static List<IShape> selectedShapes = new LinkedList<>();
    private static IShape selectedShape;

    // 1. Get all shapes drawn on canvas.
    // 2. Loop shapes to find which one intersects with the target point.
    // 3. Once found, add it to the selected shapes list.
    static void select(Point targetPoint) {
        boolean found = false;
        boolean shapeSelectedAlready = false;
        List<IShape> shapeList = ShapeList.getShapeList();
        List<IShape> selectedShapes = getSelectedShapes();
        for (IShape currentShape : shapeList) {
            String currentShapeName = currentShape.getShapeName();
            Location currentShapeLocation = currentShape.getLocation();
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
        // If nothing found but in select mode, clear the selected shapes list.
        if (!found && !shapeSelectedAlready) {
            clearSelectedShapes();
            System.out.println("Could not find shape at current location so I am going to follow most apps logic and clear the selected shapes list.");
        }
    }

    // Clears the selected shapes list.
    static void clearSelectedShapes() {
        selectedShapes = new LinkedList<>();
    }

    // Returns all the currently selected shapes.
    static List<IShape> getSelectedShapes() {
        return selectedShapes;
    }

    // Updates the latest selected shape to the shape that intersects with the target point.
    static void updateSelectedShape(Point targetPoint) {
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

    // Returns the  latest selected shape.
    static IShape getSelectedShape() {
        return selectedShape;
    }
}
