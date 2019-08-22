package model.persistence;

public class Point {

    private Point point;
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point getPoint() {
        return point;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
