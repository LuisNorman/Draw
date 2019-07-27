package controller;

import model.persistence.Location;
import model.interfaces.IShape;
import model.persistence.SelectedShape;
import model.persistence.SelectedShapes;
import model.persistence.ShapeList;
import java.util.List;

// Command Pattern
class SelectShape implements ICommand {
    private Point targetPoint;
    static final private String commandName = "Select";

    SelectShape(Point targetPoint) {
        this.targetPoint = targetPoint;
    }

    // 1. Get all shapes drawn on canvas.
    // 2. Loop shapes to find which one intersects with the target point.
    // 3. Once found, add it to the selected shapes list.
    public void execute() {
        boolean found = false;
        boolean shapeSelectedAlready = false;
        List<IShape> shapeList = ShapeList.getShapeList();
        List<IShape> selectedShapes = SelectedShapes.getAll();
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
                        SelectedShape.set(currentShape);
                        SelectedShapes.add(currentShape);
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
                        SelectedShape.set(currentShape);
                        SelectedShapes.add(currentShape);
//                        selectedShapes.add(currentShape);
                        found = true;
                        System.out.println(currentShapeName+" selected.");
                        break;
                    }
                }
            }
        }
        // If nothing found but in select mode, clear the selected shapes list.
        if (!found && !shapeSelectedAlready) {
            SelectedShapes.clear();
            System.out.println("Could not find shape at current location so I am going to follow most apps logic and clear the selected shapes list.");
        }
    }
}
