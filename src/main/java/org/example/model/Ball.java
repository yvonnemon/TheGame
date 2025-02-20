package org.example.model;

import org.example.controller.SocketManager;
import org.example.view.View;

import javax.swing.*;

public class Ball implements Runnable {
    private int x;
    private int y;
    private int speed;
    private int color;
    private int diameter;
    private int deltaX = 3;
    private int deltaY = 3;
    private boolean running = true;
    private View view;
    private SocketManager socketManager;

    public Ball(int x, int y, int speed, int color, int diameter, View view, SocketManager socketManager) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.diameter = diameter;
        this.view = view;
        this.socketManager = socketManager;
    }

    private void move(){
        x += deltaX;
        y += deltaY;
        checkCollision();
        SwingUtilities.invokeLater(view::repaint);
    }

    @Override
    public void run() {
        while (running) {
            move();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void checkCollision() {
        if (x <= 0 || x + diameter >= view.getWidth()) {
            socketManager.sendBallData(x, y, speed, diameter);
            running = false; // Stop ball on this instance
            System.out.println("Ball hit the horizontal border at (" + x + ", " + y + ")");
        }
        if (y <= 0 || y + diameter >= view.getHeight()) {
            deltaY = -deltaY;
            System.out.println("Ball hit the vertical border at (" + x + ", " + y + ")");
        }
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
