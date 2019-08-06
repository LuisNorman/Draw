package controller;

import model.interfaces.IShape;
import model.persistence.SelectedShapes;
import java.util.LinkedList;
import java.util.List;
import model.persistence.Rectangle;

public class CopyCommand implements ICommand {
    private static List<IShape> copiedShapes;

    public CopyCommand() {
        copiedShapes = new LinkedList<>();
    }

    @Override
    public void execute() {
        System.out.println("copying");
        List<IShape> allShapes = SelectedShapes.getAll();
        for(IShape shape: allShapes) {
            copiedShapes.add(new Rectangle(shape));
        }
    }

    public static List<IShape> getCopiedShapes() {
        return copiedShapes;
    }
}
