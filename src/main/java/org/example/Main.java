package org.example;

import org.example.controller.GameController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        GameController gameController = new GameController(5000,"localhost", 5001, true, "Pantalla 1");
        GameController gc = new GameController(5001,"localhost", 5000, false, "Pantalla 2");


    }
}