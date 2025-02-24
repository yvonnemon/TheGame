package org.example.controller;

import org.example.model.Ball;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager {
    private int port;
    private String otherHost;
    private int otherPort;
    private GameController gameController;

    public SocketManager(int port, String otherHost, int otherPort, GameController gameController) {
        this.port = port;
        this.otherHost = otherHost;
        this.otherPort = otherPort;
        this.gameController = gameController;
        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String data = in.readLine();
                    clientSocket.close();

                    if (data != null) {
                        String[] parts = data.split(",");
                        int x = Integer.parseInt(parts[0]);
                        int y = Integer.parseInt(parts[1]);
                        int speed = Integer.parseInt(parts[2]);
                        int diameter = Integer.parseInt(parts[3]);
                        int deltaX = Integer.parseInt(parts[4]);
                        int deltaY = (parts.length > 5) ? Integer.parseInt(parts[5]) : 3;

                        System.out.println("Received ball transfer: " + data);
                        gameController.receiveBall(x, y, speed, diameter, deltaX, deltaY);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }



    public void sendBallData(int x, int y, int speed, int diameter, int deltaX, int deltaY) {
        int maxRetries = 5;
        int retryDelay = 500; // 500ms delay between retries

        for (int i = 0; i < maxRetries; i++) {
            try (Socket socket = new Socket(otherHost, otherPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println(x + "," + y + "," + speed + "," + diameter + "," + deltaX + "," + deltaY);
                System.out.println("Sent ball to other instance after " + (i + 1) + " attempt(s).");
                return; // Success, exit loop
            } catch (IOException e) {
                System.err.println("Connection attempt " + (i + 1) + " failed. Retrying...");
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ignored) {}
            }
        }
        System.err.println("Failed to send ball after multiple attempts.");
    }


    public void notifyBallTransfer(Ball ball) {
        gameController.removeBall(ball);
    }

}
