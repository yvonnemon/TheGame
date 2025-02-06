package org.example.model;

public class Ball implements Runnable {
    private int x;
    private int y;
    private int speed;
    private int color;
    private int diameter;

    public Ball(int x, int y, int speed, int color, int diameter) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.diameter = diameter;
    }

    private void move(){

    }

    @Override
    public void run() {
        move();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
}
