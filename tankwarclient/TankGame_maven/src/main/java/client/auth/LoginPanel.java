package client.auth;

import client.model.entity.Player;
import client.socket.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    JPanel parentPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JButton loginButton = new JButton("Login");
    JButton signUpButton = new JButton("Signup");
    JLabel usernameLabel = new JLabel("username");
    JLabel passwordLabel = new JLabel("password");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    public LoginPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
        GridLayout gridLayout = new GridLayout(4, 2);
        setLayout(gridLayout);
        gridLayout.setHgap(20);
        gridLayout.setVgap(10);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Bu cs soketi baska yerde acilmali. Muhtemelen client ilk calistigindaki sinifta acmalisiniz
                // ve diger sayfalarda ulasabilmeli
                ClientSocket cs = new ClientSocket("localhost", 12345);
                Player player = Player.builder().username(usernameField.getText()).password(String.valueOf(passwordField.getPassword())).build();
                cs.sendMessage("LG", player);
                System.out.println(cs.response());
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "signupPanel");
            }
        });

        loginButton.setBackground(Color.GREEN);
        signUpButton.setBackground(Color.RED);
        loginButton.setFocusable(false);
        signUpButton.setFocusable(false);
        buttonsPanel.add(loginButton);
        buttonsPanel.add(signUpButton);

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(buttonsPanel);
    }
}