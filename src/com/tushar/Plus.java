package com.tushar;

public class Plus extends Shape {
    int x1, y1, x2, y2, x3, y3, x4, y4;
    public Plus(double x, double y) {
        super(x, y, 20, 20);
    }

    public void updateCoordinates(){
        x1 = (int)this.getX();
        y1 = (int)this.getY() + 10;
        x2 = (int)this.getX();
        y2 = (int)this.getY() - 10;
        x3 = (int)this.getX() + 10;
        y3 = (int)this.getY();
        x4 = (int)this.getX() - 10;
        y4 = (int)this.getY();
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
