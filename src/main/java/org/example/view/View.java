package org.example.view;

import org.example.model.Ball;

import javax.swing.*;
import java.awt.*;


public class View extends JPanel {
    private Ball ball;

    public View() {

    }

    public View(Ball ball) {
        this.ball = ball;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        //TODO esto esta mal
        g2d.fillOval(this.ball.getX(), this.ball.getY(), this.ball.getDiameter(), this.ball.getDiameter()); //x, y, ancho, alto
    }

}
