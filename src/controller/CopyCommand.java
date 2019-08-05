package controller;

import model.interfaces.IShape;
import model.persistence.SelectedShapes;
import java.util.List;

public class CopyCommand implements ICommand {
    private List<IShape> copiedShapes;

    @Override
    public void execute() {
        System.out.println("copying");
        this.copiedShapes = SelectedShapes.getAll();
    }

    public List<IShape> getCopiedShapes() {
        return copiedShapes;
    }
}
