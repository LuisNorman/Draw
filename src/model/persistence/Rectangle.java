package model.persistence;

import model.ShapeColor;
import model.ShapeShadingType;
import model.interfaces.IApplicationState;
import model.interfaces.ILocation;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;
import java.awt.*;

class Rectangle implements IShape {
    private Graphics2D graphics2d;
    private ColorTransform colorTransform;
    private String shapeName;
    private RectangleEllipseLocation location;
    private Color primaryColor;
    private Color secondaryColor;
    private ShapeShadingType shapeShadingType;

    Rectangle(IApplicationState applicationState, PaintCanvasBase paintCanvasBase) {
        loadGraphics(paintCanvasBase);
        setPrimaryColor(applicationState.getActivePrimaryColor());
        setSecondaryColor(applicationState.getActiveSecondaryColor());
        setLocation(applicationState);
        setShapeName("Rectangle");
        setShapeShadingType(applicationState.getActiveShapeShadingType());
        build(applicationState);
    }

    @Override
    public void loadGraphics(PaintCanvasBase paintCanvasBase) {
        this.graphics2d = paintCanvasBase.getGraphics2D();
    }

    @Override
    public void switchToSecondaryColor() {
        graphics2d.setColor(secondaryColor);
    }

    @Override
    public void build(IApplicationState applicationState) {
        if (shapeShadingType == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fillRect(applicationState.getStartPoint().getX(), applicationState.getStartPoint().getY(), applicationState.getWidth(), applicationState.getHeight());
            switchToSecondaryColor();
            graphics2d.drawRect(applicationState.getStartPoint().getX(), applicationState.getStartPoint().getY(), applicationState.getWidth(), applicationState.getHeight());
        }
        else if (shapeShadingType == ShapeShadingType.FILLED_IN) {
            graphics2d.fillRect(applicationState.getStartPoint().getX(), applicationState.getStartPoint().getY(), applicationState.getWidth(), applicationState.getHeight());
        }
        else if (shapeShadingType == ShapeShadingType.OUTLINE) {
            graphics2d.drawRect(applicationState.getStartPoint().getX(), applicationState.getStartPoint().getY(), applicationState.getWidth(), applicationState.getHeight());
        }
    }

    @Override
    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    @Override
    public String getShapeName() {
        return shapeName;
    }

    @Override
    public void setLocation(IApplicationState applicationState) {
        this.location = new RectangleEllipseLocation(applicationState.getStartPoint(), applicationState.getEndPoint());
    }

    @Override
    public ILocation getLocation(){
        return location;
    }

    @Override
    public void setPrimaryColor(ShapeColor shapeColor) {
        this.colorTransform = new ColorTransform();
        this.primaryColor = colorTransform.transform(shapeColor);
        graphics2d.setColor(primaryColor);
    }

    @Override
    public Color getPrimaryColor() {
        return primaryColor;
    }

    @Override
    public void setSecondaryColor(ShapeColor shapeColor) {
        this.secondaryColor = colorTransform.transform(shapeColor);
    }

    @Override
    public Color getSecondaryColor() {
        return secondaryColor;
    }

    @Override
    public void setShapeShadingType(ShapeShadingType shapeShadingType) {
        this.shapeShadingType = shapeShadingType;
    }

    @Override
    public ShapeShadingType getShapeShadingType() {
        return shapeShadingType;
    }

}



