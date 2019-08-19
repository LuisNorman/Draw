package controller.command;

import model.interfaces.IShape;
import model.persistence.ShapeGroup;
import model.persistence.Groups;
import model.persistence.SelectedShapes;

public class GroupCommand implements ICommand {

    @Override
    public void execute() {
        // Group all the selected shapes and add it into groups
        ShapeGroup newShapeGroup = new ShapeGroup(SelectedShapes.getAll());
        for (IShape shape: SelectedShapes.getAll()) {
            System.out.println("Grouping " +shape.getShapeType());
        }
        Groups.add(newShapeGroup);
    }
}
