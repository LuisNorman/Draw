package model.interfaces;

import model.ShapeColor;
import model.ShapeShadingType;
import view.interfaces.PaintCanvasBase;
import java.awt.Color;

public interface IShape {
    void setShapeName(String shapeName);
    String getShapeName();

    void switchToSecondaryColor();

    void setPrimaryColor(ShapeColor shapeColor);
    Color getPrimaryColor();

    void setSecondaryColor(ShapeColor shapeColor);
    Color getSecondaryColor();

    void setShapeShadingType(ShapeShadingType shapeShadingType);
    ShapeShadingType getShapeShadingType();

    void setLocation(IApplicationState applicationState);
    ILocation getLocation();

    void loadGraphics(PaintCanvasBase paintCanvasBase);

    void build(IApplicationState applicationState);
}
