package org.example.controller;

import org.example.model.Ball;
import org.example.view.View;

import javax.swing.*;

public class GameController { //THINK podria ser static tbh
    private View canvas;
    private Ball ball;

    public GameController() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drawing Canvas");
            canvas = new View();
            ball = new Ball(100, 100, 5, 20,20, canvas);
            canvas.setBall(ball);

            frame.add(canvas);
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            Thread ballThread = new Thread(ball);
            ballThread.start();
        });
    }

    public void repaintCanvas() {
        canvas.repaint();
    }

}
