package model.persistence;

import model.interfaces.IShape;
import java.util.LinkedList;
import java.util.List;

public class SelectedShapes {
    static List<IShape>  selectedShapes = new LinkedList<IShape>();

    public static void add(IShape shape) {
        selectedShapes.add(shape);
    }

    public static List<IShape> getAll() {
        return selectedShapes;
    }

    public static void clear() {
        selectedShapes = new LinkedList<IShape>();
    }

}
