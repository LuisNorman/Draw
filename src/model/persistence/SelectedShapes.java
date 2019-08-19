package model.persistence;

import model.interfaces.IShape;
import java.util.LinkedList;
import java.util.List;

public class SelectedShapes {
    private static List<IShape>  selectedShapes = new LinkedList<IShape>();

    public static void add(IShape shape) {
        selectedShapes.add(shape);
    }

    public static void addAll(List<IShape> shapes) {
        for (IShape shape: shapes) {
            if (!selectedShapes.contains(shape)) {
                selectedShapes.add(shape);
            }
        }
    }

    public static List<IShape> getAll() {
        return selectedShapes;
    }

    public static void clear() {
        System.out.println("size in object: "+selectedShapes.size());
        selectedShapes = new LinkedList<>();
    }

    private static boolean contains(IShape shape) {
        return selectedShapes.contains(shape);
    }

    public static void remove(IShape shape) {
        if (contains(shape)) {
            selectedShapes.remove(shape);
        }
    }

}
