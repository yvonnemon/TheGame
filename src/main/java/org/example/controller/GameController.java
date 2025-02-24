package org.example.controller;

import org.example.model.Ball;
import org.example.view.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private View canvas;
    private Ball ball;
    private List<Ball> balls = new ArrayList<>();
    private SocketManager socketManager;
    private boolean isPrimaryInstance;

    public GameController(int port, String otherHost, int otherPort, boolean isPrimaryInstance, String name) {
        this.isPrimaryInstance = isPrimaryInstance;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(name);
            canvas = new View();
            socketManager = new SocketManager(port, otherHost, otherPort, this);

          //  ball = new Ball(100, 100, 5, 20,20, canvas, socketManager);

          //  canvas.setBall(ball);
            frame.add(canvas);
            frame.setSize(750, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            if (isPrimaryInstance) {
                frame.setLocation(100, 100); // Left side of screen
            } else {
                frame.setLocation(900, 100); // Right side of screen
            }
            frame.setVisible(true);

            if (isPrimaryInstance) {
                startBall(100, 100, 15, 30, 5, 5); // Only start the ball in primary instance
                startBall(45, 153, 20, 30, 15, 3); // Only start the ball in primary instance
                startBall(45, 100, 30, 30, -15, 3); // Only start the ball in primary instance
            }

            /*Thread ballThread = new Thread(ball);
            ballThread.start();*/
        });
    }

    public void startBall(int x, int y, int speed, int diameter, int deltaX, int deltaY) {
        Ball ball = new Ball(x, y, speed, 20, diameter, canvas, socketManager, isPrimaryInstance);
        ball.setDeltaX(deltaX);
        ball.setDeltaY(deltaY);
        balls.add(ball); // Store multiple balls
        // Update the View with all balls
        new Thread(ball).start();
//        Ball ball1 = new Ball(75, 150, speed, 20, diameter, canvas, socketManager, isPrimaryInstance);
//        ball1.setDeltaX(deltaX);
//        balls.add(ball1);
//        new Thread(ball1).start();

        canvas.setBallList(balls);
//        if (ball == null || !ball.isRunning()) { // Prevent restarting
//            System.out.println("Starting new ball at: (" + x + ", " + y + "), moving " + (deltaX > 0 ? "right" : "left"));
//            ball = new Ball(x, y, speed, 20, diameter, canvas, socketManager, isPrimaryInstance);
//            ball.setDeltaX(deltaX); // Set correct movement direction
//
//            canvas.setBall(ball);
//            new Thread(ball).start();
//            Ball ball2 = new Ball(75, 150, speed, 20, diameter, canvas, socketManager, isPrimaryInstance);
//            ball2.setDeltaX(deltaX); // Set correct movement direction
//            canvas.setBall(ball2);
//
//            new Thread(ball2).start();
//
//            List<Ball> pelotas = new ArrayList<>();
//            pelotas.add(ball);
//            pelotas.add(ball2);
//            canvas.setBallList(pelotas);
//        }
    }

    public void receiveBall(int x, int y, int speed, int diameter, int deltaX, int deltaY) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Received ball at (" + x + ", " + y + ") moving " + (deltaX > 0 ? "right" : "left"));
            startBall(x, y, speed, diameter, deltaX, deltaY); // Pass direction
        });
    }

    public void removeBall(Ball ball) {
        SwingUtilities.invokeLater(() -> {
            if (balls.contains(ball)) {
                balls.remove(ball);
                System.out.println("Ball removed from view.");
                canvas.setBallList(balls); // Update the view with the new list
                canvas.repaint(); // Ensure the ball disappears visually
            }
        });
    }

    public void repaintCanvas() {
        canvas.repaint();
    }

}
