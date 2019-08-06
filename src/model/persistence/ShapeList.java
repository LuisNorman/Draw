package model.persistence;

import controller.Point;
import model.interfaces.IShape;
import java.util.LinkedList;

public class ShapeList {
    private static LinkedList<IShape> shapeList = new LinkedList<>();

    public static void add(IShape shape) {
        shapeList.add(shape);
    }

    public static void remove(IShape targetShape) {
        for (IShape currentShape : shapeList) {
            if (currentShape == targetShape) {
                shapeList.remove(targetShape);
            }
        }
    }

    public static void updateStartPoint(IShape targetShape, Point startPoint) {
        for (IShape currentShape : shapeList) {
            if (currentShape == targetShape) {
                currentShape.getLocation().setStartPoint(startPoint);
            }
        }
    }

    public static void updateEndPoint(IShape targetShape, Point endPoint) {
        for (IShape currentShape : shapeList) {
            if (currentShape == targetShape) {
                currentShape.getLocation().setEndPoint(endPoint);
            }
        }
    }

    public static LinkedList<IShape> getShapeList() {
        return shapeList;
    }

}
