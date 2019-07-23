package model.persistence;

import model.ShapeColor;
import model.ShapeShadingType;
import model.interfaces.IApplicationState;
import model.interfaces.ILocation;
import model.interfaces.IShape;
import view.interfaces.PaintCanvasBase;
import java.awt.*;

public class Triangle implements IShape {
    private Graphics2D graphics2d;
    private ColorTransform colorTransform;
    private String shapeName;
    private TriangleLocation location;
    private Color primaryColor;
    private Color secondaryColor;
    private ShapeShadingType shapeShadingType;
    private int[] x;
    private int[] y;


    public Triangle(IApplicationState applicationState, PaintCanvasBase paintCanvasBase) {
        loadGraphics(paintCanvasBase);
        setPrimaryColor(applicationState.getActivePrimaryColor());
        setSecondaryColor(applicationState.getActiveSecondaryColor());
        setupCoordinates(applicationState.getStartPoint().getX(), applicationState.getEndPoint().getX(), applicationState.getStartPoint().getX() + (applicationState.getStartPoint().getX() - applicationState.getEndPoint().getX()), applicationState.getStartPoint().getY(), applicationState.getEndPoint().getY(), applicationState.getEndPoint().getY());
        setLocation(applicationState);
        setShapeName("Triangle");
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
    public void setLocation(IApplicationState applicationState) {
        this.location = new TriangleLocation(applicationState.getStartPoint(), applicationState.getEndPoint());
    }

    @Override
    public ILocation getLocation() {
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
    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    @Override
    public String getShapeName() {
        return shapeName;
    }

}


