package controller;

import model.interfaces.IShape;
import model.persistence.Ellipse;
import model.persistence.SelectedShapes;
import java.util.LinkedList;
import java.util.List;
import model.persistence.Rectangle;
import model.persistence.Triangle;

public class CopyCommand implements ICommand {
    private static List<IShape> copiedShapes;

    public CopyCommand() {
        copiedShapes = new LinkedList<>();
    }

    @Override
    public void execute() {
        List<IShape> allShapes = SelectedShapes.getAll();
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

    public static List<IShape> getCopiedShapes() {
        return copiedShapes;
    }
}
