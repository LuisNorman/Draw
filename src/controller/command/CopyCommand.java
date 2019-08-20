package controller.command;

import model.interfaces.IShape;
import model.persistence.*;

import java.util.LinkedList;
import java.util.List;

public class CopyCommand implements ICommand {
    private static List<IShape> copiedShapes;
    private static String commandName = "Copy";

    public CopyCommand() {
        copiedShapes = new LinkedList<>();
    }

    @Override
    public void execute() {
        List<IShape> allShapes = SelectedShapes.getAll();
        addShapesInGroup(allShapes);

        for(IShape shape: allShapes) {
            switch(shape.getShapeType()) {
                case TRIANGLE:
                    copiedShapes.add(new Triangle(shape));
                    break;

                case RECTANGLE:
                    copiedShapes.add(new Rectangle(shape));
                    break;

                case ELLIPSE:
                    copiedShapes.add(new Ellipse(shape));
                    break;
            }
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    public static List<IShape> getCopiedShapes() {
        return copiedShapes;
    }

    // Checks if a shape is apart of a group
    // If so, it will add the grouped shapes to the selected list.
    private static void addShapesInGroup(List<IShape> selectedShapes) {
        int size = selectedShapes.size();
        List<IShape> tempShapes = selectedShapes;
        for (int i=0; i<size; i++) {
            IShape currentShape = tempShapes.get(i);
            for (ShapeGroup group: Groups.getGroups()) {
                if (group.contains(currentShape)) {
                    List<IShape> shapeGroup = group.getShapeGroup();
                    for (IShape shape: shapeGroup) {
                        if (!selectedShapes.contains(shape)) {
                            selectedShapes.add(shape);
                        }
                    }
                }
            }
        }
    }

}
