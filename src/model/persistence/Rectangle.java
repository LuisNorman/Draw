package model.persistence;

import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;
import java.awt.Color;
import java.awt.Graphics2D;
import controller.Point;


public class Rectangle implements IShape {
    private Graphics2D graphics2d;
    private TransformColor transformColor;
    private Location location;
    private Color primaryColor;
    private Color secondaryColor;
    private ShapeShadingType shapeShadingType;
    private ShapeType shapeType;
    private int width;
    private int height;

    Rectangle(IApplicationState applicationState, PaintCanvasBase paintCanvasBase) {
        loadGraphics(paintCanvasBase);
        setPrimaryColor(applicationState.getActivePrimaryColor());
        setSecondaryColor(applicationState.getActiveSecondaryColor());
        setLocation(applicationState.getStartPoint(), applicationState.getEndPoint());
        setShapeType(ShapeType.RECTANGLE);
        setShapeShadingType(applicationState.getActiveShapeShadingType());
        setHeight(applicationState.getHeight());
        setWidth(applicationState.getWidth());
        build(applicationState);
    }

    public Rectangle(IShape shape) {
        this.location = shape.getLocation();
        this.primaryColor = shape.getPrimaryColor();
        this.secondaryColor = shape.getSecondaryColor();
        this.shapeShadingType = shape.getShapeShadingType();
        this.shapeType = shape.getShapeType();
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
    public void setLocation(Point startPoint, Point endPoint) {
        this.location= new Location(startPoint, endPoint);
    }

    @Override
    public Location getLocation(){
        return location;
    }

    @Override
    public void setPrimaryColor(ShapeColor shapeColor) {
        this.transformColor = new TransformColor();
        this.primaryColor = transformColor.transform(shapeColor);
        graphics2d.setColor(primaryColor);
    }

    @Override
    public Color getPrimaryColor() {
        return primaryColor;
    }

    @Override
    public void setSecondaryColor(ShapeColor shapeColor) {
        this.secondaryColor = transformColor.transform(shapeColor);
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

    @Override
    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    public ShapeType getShapeType() {
        return shapeType;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }



}



