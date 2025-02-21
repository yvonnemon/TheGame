package org.example.view;

import org.example.model.Ball;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class View extends JPanel {
    private Ball ball;
    private List<Ball> ballList;

    public View() {

    }

    public View(Ball ball) {
        this.ball = ball;

    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public List<Ball> getBallList() {
        return ballList;
    }

    public void setBallList(List<Ball> ballList) {
        this.ballList = ballList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(ballList != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.PINK);

            for (Ball ball : ballList) {
                g2d.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());
            }
        }

    }
}
