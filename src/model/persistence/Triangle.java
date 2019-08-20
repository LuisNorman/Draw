package model.persistence;

import controller.Point;
import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.IApplicationState;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;
import java.awt.Graphics2D;
import java.awt.Color;

public class Triangle implements IShape {
    private Graphics2D graphics2d;
    private TransformColor transformColor;
    private Location location;
    private Color primaryColor;
    private Color secondaryColor;
    private ShapeShadingType shapeShadingType;
    private int[] x;
    private int[] y;
    private ShapeType shapeType;
    private int width;
    private int height;
    private IApplicationState applicationState;

    public Triangle(IApplicationState applicationState, PaintCanvasBase paintCanvasBase) {
        loadGraphics(paintCanvasBase);
        setPrimaryColor(applicationState.getActivePrimaryColor());
        setSecondaryColor(applicationState.getActiveSecondaryColor());
        setupCoordinates(applicationState.getStartPoint().getX(), applicationState.getEndPoint().getX(), applicationState.getStartPoint().getX() + (applicationState.getStartPoint().getX() - applicationState.getEndPoint().getX()), applicationState.getStartPoint().getY(), applicationState.getEndPoint().getY(), applicationState.getEndPoint().getY());
        setLocation(applicationState.getStartPoint(), applicationState.getEndPoint());
        setShapeType(ShapeType.TRIANGLE);
        setShapeShadingType(applicationState.getActiveShapeShadingType());
        setShapeType(applicationState.getActiveShapeType());
        setHeight(applicationState.getHeight());
        setWidth(applicationState.getWidth());
        build(applicationState);
        this.applicationState = applicationState;
    }

    public Triangle(IShape shape) {
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
    public void setLocation(Point startPoint, Point endPoint) {
        this.location = new Location(startPoint, endPoint);
    }

    @Override
    public Location getLocation() {
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
    public void build(IApplicationState applicationState) {
        int n = 3;
        if (shapeShadingType == ShapeShadingType.OUTLINE_AND_FILLED_IN) {
            graphics2d.fillPolygon(x, y, n);
            switchToSecondaryColor();
            graphics2d.drawPolygon(x, y, n);
        }
        else if (shapeShadingType == ShapeShadingType.FILLED_IN) {
            graphics2d.fillPolygon(x, y, n);
        }
        else if (shapeShadingType == ShapeShadingType.OUTLINE) {
            graphics2d.drawPolygon(x, y, n);
        }
    }

    private void setupCoordinates(int x1, int x2, int x3, int y1, int y2, int y3) {
        x = new int[]{x1, x2, x3};
        y = new int[]{y1, y2, y3};
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

    @Override
    public IApplicationState getApplicationState() {
        return applicationState;
    }

}


