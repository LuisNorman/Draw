package model.interfaces;

import controller.Point;

public interface ILocation {
    public void setStartPoint(Point startPoint);
    public void setEndPoint(Point endPoint);
    public Point getStartPoint();
    public Point getEndPoint();
}
