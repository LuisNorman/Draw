package controller;

import model.persistence.ShapeGroup;
import model.persistence.Groups;
import model.persistence.SelectedShapes;

public class GroupCommand implements ICommand {

    @Override
    public void execute() {
        ShapeGroup newShapeGroup = new ShapeGroup(SelectedShapes.getAll());
        Groups.add(newShapeGroup);
    }
}
