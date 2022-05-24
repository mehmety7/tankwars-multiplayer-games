package client.screens.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPanel extends JPanel {
    JPanel parentPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JButton loginButton = new JButton("Login");
    JButton signUpButton = new JButton("Sign up");
    JLabel usernameLabel = new JLabel("username");
    JLabel passwordLabel = new JLabel("password");
    JLabel passwordAgainLabel = new JLabel("password again");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField passwordAgainField = new JPasswordField();

    public SignupPanel(JPanel parentPanel) {
        GridLayout gridLayout = new GridLayout(4, 2);
        setLayout(gridLayout);
        gridLayout.setHgap(20);
        gridLayout.setVgap(10);

        loginButton.setBackground(Color.RED);
        signUpButton.setBackground(Color.GREEN);
        loginButton.setFocusable(false);
        signUpButton.setFocusable(false);
        buttonsPanel.add(signUpButton);
        buttonsPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "loginPanel");
            }
        });

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(passwordAgainLabel);
        this.add(passwordAgainField);
        this.add(buttonsPanel);
    }
}
