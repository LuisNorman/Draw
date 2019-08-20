package controller.command;

import controller.Point;
import controller.command.ICommand;
import model.interfaces.IShape;
import model.persistence.*;

import java.util.List;

// Command Pattern
// Select the shapes within the invisible box
public class SelectCommand implements ICommand {

    static final private String commandName = "Select";
    private Point startPoint;
    private Point endPoint;
    private int width;
    private int height;

    public SelectCommand(Point startPoint, Point endPoint) {
        if (startPoint.getX() > endPoint.getX()) {
            int temp = startPoint.getX();
            startPoint.setX(endPoint.getX());
            endPoint.setX(temp);
        }
        if (startPoint.getY() > endPoint.getY()) {
            int temp = startPoint.getY();
            startPoint.setY(endPoint.getY());
            endPoint.setY(temp);
        }
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.width = calculateWidth(startPoint, endPoint);
        this.height = calculateHeight(startPoint, endPoint);
    }

    public void execute() {
        boolean found = false;
        boolean shapeSelectedAlready = false;
        List<IShape> shapeList = ShapeList.getShapeList();
        List<IShape> selectedShapes = SelectedShapes.getAll();
        for (IShape currentShape : shapeList) {
            Point currentShapeStartPoint = currentShape.getLocation().getStartPoint();
            if (startPoint.getX() < currentShapeStartPoint.getX() + currentShape.getWidth() &&
                    startPoint.getX() + width > currentShapeStartPoint.getX() &&
                    startPoint.getY() < currentShapeStartPoint.getY() + currentShape.getHeight() &&
                    startPoint.getY() + height > currentShapeStartPoint.getY()) {

                // Maybe keep this logic inside selected shapes
                if (selectedShapes.contains(currentShape)){
                    shapeSelectedAlready = true;
                    System.out.println("Shape selected already.");
                }
                else {
                    SelectedShapes.add(currentShape);
                    SelectedShape.set(currentShape);
                    found = true;
                    System.out.println(currentShape.getShapeType()+" selected.");
                }
            }
        }
        // If nothing found but in select mode, clear the selected shapes list.
        if (!found && !shapeSelectedAlready) {
            SelectedShapes.clear();
            System.out.println("Could not find shape at current location so I am going to follow most apps logic and clear the selected shapes list.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    private int calculateWidth(Point startPoint, Point endPoint) {
        return endPoint.getX() - startPoint.getX();
    }

    private int calculateHeight(Point startPoint, Point endPoint) {
        return endPoint.getY() - startPoint.getY();
    }


    // NOT BEING UTILIZED - THIS IS FOR SINGLE/PRECISE SELECTS

//import model.ShapeType;
//import model.persistence.Location;

//    private Point targetPoint;

//    SelectCommand(Point targetPoint) {
//        this.targetPoint = targetPoint;
//    }

    // 1. Get all shapes drawn on canvas.
    // 2. Loop shapes to find which one intersects with the target point.
    // 3. Once found, add it to the selected shapes list.
//    public void single_select() {
//        boolean found = false;
//        boolean shapeSelectedAlready = false;
//        List<IShape> shapeList = ShapeList.getShapeList();
//        List<IShape> selectedShapes = SelectedShapes.getAll();
//        int i=1;
//        for (IShape currentShape : shapeList) {
//            String currentShapeName = currentShape.getShapeName();
//            Location currentShapeLocation = currentShape.getLocation();
//            Point currentShapeStartPoint = currentShapeLocation.getStartPoint();
//            Point currentShapeEndPoint = currentShapeLocation.getEndPoint();
//            if (currentShapeName.equals("Triangle")) {
//                if ((currentShapeStartPoint.getX() + (currentShapeStartPoint.getX() - currentShapeEndPoint.getX()) <= targetPoint.getX()) && (targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
//                    if (selectedShapes.contains(currentShape)){
//                        shapeSelectedAlready = true;
//                        System.out.println("Shape selected already.");
//                    }
//                    else {
//                        SelectedShape.set(currentShape);
//                        SelectedShapes.add(currentShape);
//                        found = true;
//                        System.out.println(currentShapeName+" selected.");
//                        break;
//                    }
//                }
//            }
//            else if (currentShapeName.equals("Rectangle") || currentShapeName.equals("Ellipse")){
//                if ((currentShapeStartPoint.getX() <= targetPoint.getX() && targetPoint.getX() <= currentShapeEndPoint.getX()) && (currentShapeStartPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= currentShapeEndPoint.getY())) {
//                    if (selectedShapes.contains(currentShape)){
//                        shapeSelectedAlready = true;
//                        System.out.println("Shape selected already.");
//                    }
//                    else {
//                        SelectedShape.set(currentShape);
//                        SelectedShapes.add(currentShape);
//                        found = true;
//                        System.out.println(currentShapeName+" selected.");
//                        break;
//                    }
//                }
//            }
//        }
//        // If nothing found but in select mode, clear the selected shapes list.
//        if (!found && !shapeSelectedAlready) {
//            SelectedShapes.clear();
//            System.out.println("Could not find shape at current location so I am going to follow most apps logic and clear the selected shapes list.");
//        }
//    }


}
