package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Tank Game");

//        GamePanel gamePanel = new GamePanel(4);
//        window.add(gamePanel);
        window.pack(); // fit
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
