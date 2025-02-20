package org.example.controller;

import org.example.model.Ball;
import org.example.view.View;

import javax.swing.*;

public class GameController {
    private View canvas;
    private Ball ball;
    private SocketManager socketManager;
    private boolean isPrimaryInstance;

    public GameController(int port, String otherHost, int otherPort, boolean isPrimaryInstance) {
        this.isPrimaryInstance = isPrimaryInstance;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drawing Canvas");
            canvas = new View();
            socketManager = new SocketManager(port, otherHost, otherPort, this);

          //  ball = new Ball(100, 100, 5, 20,20, canvas, socketManager);

          //  canvas.setBall(ball);
            frame.add(canvas);
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            if (isPrimaryInstance) {
                startBall(100, 100, 5, 20); // Only start the ball in primary instance
            }

            /*Thread ballThread = new Thread(ball);
            ballThread.start();*/
        });
    }
    public void startBall(int x, int y, int speed, int diameter) {
        if (ball == null || !ball.isRunning()) { // Prevent restarting
            System.out.println("Starting new ball at: (" + x + ", " + y + ")");
            ball = new Ball(x, y, speed, 20,diameter, canvas, socketManager);
            canvas.setBall(ball);
            new Thread(ball).start();
        }
    }

    public void receiveBall(int x, int y, int speed, int diameter) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Received ball, creating new instance...");
            ball = new Ball(100, 100, 5, 20,20, canvas, socketManager);
            canvas.setBall(ball);
            new Thread(ball).start();
        });
    }

    public void repaintCanvas() {
        canvas.repaint();
    }

}
