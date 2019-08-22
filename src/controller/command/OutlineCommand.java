package controller.command;

import controller.*;
import model.ShapeShadingType;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import model.persistence.*;
import view.interfaces.PaintCanvasBase;
import java.util.List;

public class OutlineCommand implements ICommand {
    private final PaintCanvasBase paintCanvas;
    private final IApplicationState applicationState;
    private static final String commandName = "Outline";
    private IShape currentShape;

    public OutlineCommand(PaintCanvasBase paintCanvas, IApplicationState applicationState, IShape currentShape) {
        this.paintCanvas = paintCanvas;
        this.applicationState = applicationState;
        this.currentShape = currentShape;
    }

    @Override
    public void execute() {
//        List<IShape> selectedShapes = SelectedShapes.getAll();
//        addShapesInGroup(selectedShapes);
        List<IShape> shapeList = ShapeList.getShapeList();
//        for (IShape currentShape : selectedShapes) {
            System.out.println("Outlining "+currentShape.getShapeType());
            IShape iShape = null;
            Point startPoint = currentShape.getLocation().getStartPoint();
            Point endPoint = currentShape.getLocation().getEndPoint();
            Shape shape = new Shape(paintCanvas, currentShape, startPoint, endPoint, applicationState);
            IShapeStrategy iShapeStrategy = null;

            switch(currentShape.getShapeType()) {

                case TRIANGLE :
                    iShapeStrategy = new TriangleStrategy();
                    if (!shapeList.contains(currentShape)) {
                        iShape = new Triangle(currentShape);
                    }
                    break;

                case RECTANGLE :
                    iShapeStrategy = new RectangleStrategy();
                    if (!shapeList.contains(currentShape)) {
                        iShape = new Rectangle(currentShape);
                    }
                    break;

                case ELLIPSE :
                    iShapeStrategy = new EllipseStrategy();

                    if (!shapeList.contains(currentShape)) {
                        iShape = new Ellipse(currentShape);
                    }
                    break;
            }
            if (!shapeList.contains(currentShape) && iShape != null) {
                iShape.setShapeShadingType(ShapeShadingType.OUTLINE);
                shapeList.add(iShape);
            }

            shape.outline(iShapeStrategy);
//        }
    }

    @Override
    public String getCommandName() {
        return commandName;
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
