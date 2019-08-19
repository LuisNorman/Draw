package model.persistence;

import java.util.LinkedList;
import java.util.List;

public class Groups {
    static List<ShapeGroup> groups = new LinkedList<>();

    public static void add(ShapeGroup shapeGroup) {
        groups.add(shapeGroup);
    }

    public static List<ShapeGroup> getGroups() {
        return groups;
    }


}
