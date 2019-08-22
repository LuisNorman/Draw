package model.persistence;

import model.interfaces.IShape;
import java.util.ArrayList;

public class ShapeList {
    private static ArrayList<IShape> shapeList = new ArrayList<>();

    public static void add(IShape shape) {
        shapeList.add(shape);
    }

    public static void remove(IShape targetShape) {
        for (int i=0; i<shapeList.size(); i++) {
            IShape currentShape = shapeList.get(i);
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

    public static ArrayList<IShape> getShapeList() {
        return shapeList;
    }

}
