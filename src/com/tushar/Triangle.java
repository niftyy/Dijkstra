package com.tushar;

public class Triangle extends Shape{
    int x1, y1, x2, y2, x3, y3;
    Triangle(double x, double y){
        super(x, y, 20, 20);
       updateCoordinates();
    }

    public void updateCoordinates(){
        double dx = (double) 15 / 1.414;
        double dy = dx;
        x1 = (int) Math.round(x + dx);
        y1 = (int) Math.round(y + dy);
        x2 = (int) Math.round(x - dx);
        y2 = (int) Math.round(y - dy);
        x3 = (int) Math.round(x + dx);
        y3 = (int) Math.round(y - dy);
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
}
