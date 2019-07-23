package model.persistence;

import controller.Point;
import model.interfaces.ILocation;

public class RectangleEllipseLocation implements ILocation {
    Point startPoint;
    Point endPoint;

    public RectangleEllipseLocation(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public Point getStartPoint(){
        return startPoint;
    }

    @Override
    public Point getEndPoint() {
        return endPoint;
    }

}
