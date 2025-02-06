package org.example.controller;

import org.example.model.Ball;
import org.example.view.View;

import javax.swing.*;

public class GameController { //THINK podria ser static tbh
    private View canvas;

    public GameController() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drawing Canvas");
            Ball ball = new Ball(100,100, 5, 000000, 20);
            canvas = new View( ball );


            frame.add(canvas);
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public void repaintCanvas() {
        canvas.repaint();
    }

}
