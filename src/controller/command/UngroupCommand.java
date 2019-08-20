package controller.command;

import controller.CommandHistory;
import controller.UndoCommandHistory;
import model.interfaces.IShape;
import model.persistence.ShapeGroup;
import model.persistence.Groups;
import java.util.HashMap;
import java.util.List;

public class UngroupCommand implements ICommand {
    HashMap<IShape, ShapeGroup> ungroupedShapes;
    private List<IShape> shapesToUngroup;
    private final static String commandName = "Ungroup";

    public UngroupCommand(List<IShape> shapesToUngroup) {
        this.shapesToUngroup = shapesToUngroup;
    }

    @Override
    // Ungroup the selected rectangles
    public void execute() {
        HashMap<IShape, ShapeGroup> temp = new HashMap<>();
//        List<IShape> selectedShapes = SelectedShapes.getAll();
        List<ShapeGroup> groups = Groups.getGroups();
        for (int i=0; i<shapesToUngroup.size(); i++) {
            IShape currentShape =  shapesToUngroup.get(i);
            for (int j=0; j<groups.size(); j++) {
                ShapeGroup currentGroup = groups.get(j);
                if (currentGroup.contains(currentShape)) {
                    temp.put(currentShape, currentGroup);
                    currentGroup.delete(currentShape);
                    System.out.println("Ungrouping "+currentShape.getShapeType());
                    break;
                }
            }
        }
        storeUngroupedShapes(temp);
        if (!UndoCommandHistory.contains(this)) {
            CommandHistory.add(this);
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    private void storeUngroupedShapes(HashMap<IShape, ShapeGroup> ungroupedShapes) {
        this.ungroupedShapes = ungroupedShapes;
    }

    HashMap<IShape, ShapeGroup> getUngroupedShapes() {
        return ungroupedShapes;
    }
}
