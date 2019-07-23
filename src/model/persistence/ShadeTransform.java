package model.persistence;

import model.ShapeShadingType;
import model.interfaces.ITransform;

import java.util.EnumMap;

public class ShadeTransform implements ITransform {
    EnumMap<ShapeShadingType, String> shadingMap;
}
