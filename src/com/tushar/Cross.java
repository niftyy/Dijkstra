package com.tushar;

public class Cross extends Shape {
    int x1, y1, x2, y2, x3, y3, x4, y4;
    public Cross(double x, double y) {
        super(x, y, 20, 20);
    }

    public void updateCoordinates(){
        double dx = (double) 10 / 1.414;
        double dy = dx;
        x1 = (int)Math.round(this.getX() + dx);
        y1 = (int)Math.round(this.getY() + dy);
        x2 = (int)Math.round(this.getX() - dx);
        y2 = (int)Math.round(this.getY() - dy);
        x3 = (int)Math.round(this.getX() + dx);
        y3 = (int)Math.round(this.getY() - dy);
        x4 = (int)Math.round(this.getX() - dx);
        y4 = (int)Math.round(this.getY() + dy);
    }

    public int getX1() {
        updateCoordinates();
        return x1;
    }

    public int getY1() {
        updateCoordinates();
        return y1;
    }

    public int getX2() {
        updateCoordinates();
        return x2;
    }

    public int getY2() {
        updateCoordinates();
        return y2;
    }

    public int getX3() {
        updateCoordinates();
        return x3;
    }

    public int getY3() {
        updateCoordinates();
        return y3;
    }

    public int getX4() {
        updateCoordinates();
        return x4;
    }

    public int getY4() {
        updateCoordinates();
        return y4;
    }
}
