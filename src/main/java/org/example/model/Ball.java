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
    private boolean isPrimaryInstance;
    private long lastTransferTime = 0;
    private static final int TRANSFER_COOLDOWN = 1000;
    private static final int BORDER_OFFSET = 5;

    public Ball(int x, int y, int speed, int color, int diameter, View view, SocketManager socketManager, boolean isPrimaryInstance) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.diameter = diameter;
        this.view = view;
        this.socketManager = socketManager;
        this.isPrimaryInstance = isPrimaryInstance;
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
        long currentTime = System.currentTimeMillis();

        if (isPrimaryInstance) {
            if (x + diameter >= view.getWidth()) { // Transfer to screen2
                if (currentTime - lastTransferTime > TRANSFER_COOLDOWN) {
                    lastTransferTime = currentTime;
                    int newX = BORDER_OFFSET;
                    socketManager.sendBallData(newX, y, speed, diameter, deltaX);

                    running = false; // Stop the ball's movement
                    socketManager.notifyBallTransfer(this); // Tell GameController to remove it
                    System.out.println("Ball sent to screen2 and removed from screen1.");
                }
            } else if (x <= 0) { // Bounce on left wall of screen1
                deltaX = -deltaX;
                System.out.println("Ball hit the left wall of screen1 and bounced.");
            }
        } else {
            if (x + diameter >= view.getWidth()) { // Bounce on right wall of screen2
                deltaX = -deltaX;
                System.out.println("Ball hit the right wall of screen2 and bounced.");
            } else if (x <= 0) { // Transfer back to screen1
                if (currentTime - lastTransferTime > TRANSFER_COOLDOWN) {
                    lastTransferTime = currentTime;
                    int newX = view.getWidth() - BORDER_OFFSET - diameter;
                    socketManager.sendBallData(newX, y, speed, diameter, deltaX);

                    running = false; // Stop the ball's movement
                    socketManager.notifyBallTransfer(this); // Tell GameController to remove it
                    System.out.println("Ball sent back to screen1 and removed from screen2.");
                }
            }
        }


        if (y <= 0 || y + diameter >= view.getHeight()) {
            deltaY = -deltaY;
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

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }
}
