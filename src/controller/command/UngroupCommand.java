package controller.command;

import model.interfaces.IShape;
import model.persistence.ShapeGroup;
import model.persistence.Groups;
import model.persistence.SelectedShapes;
import java.util.List;

public class UngroupCommand implements ICommand {

    @Override
    // Ungroup the selected rectangles
    public void execute() {
        List<IShape> selectedShapes = SelectedShapes.getAll();
        List<ShapeGroup> groups = Groups.getGroups();
        for (IShape currentShape: selectedShapes) {
            for (ShapeGroup currentGroup: groups) {
                if (currentGroup.contains(currentShape)) {
                    currentGroup.delete(currentShape);
                    System.out.println("Ungrouping "+currentShape.getShapeType());
                    break;
                }
            }
        }
    }
}