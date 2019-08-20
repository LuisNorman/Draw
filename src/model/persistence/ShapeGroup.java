package model.persistence;

import model.interfaces.IShape;
import java.util.List;

public class ShapeGroup {
    List<IShape> shapeGroup;

    public ShapeGroup(List<IShape> selectedShapes) {
        this.shapeGroup = selectedShapes;
    }

    public List<IShape> getShapeGroup() {
        return shapeGroup;
    }

    public boolean contains(IShape shape) {
        return shapeGroup.contains(shape);
    }

    public void delete(IShape shape) {
        shapeGroup.remove(shape);
    }

    public void add(IShape shape) {
        this.shapeGroup.add(shape);
    }



}
