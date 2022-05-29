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
    JLabel passwordAgainLabel = new JLabel("password");
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

        JPanel usernamePanel = new JPanel(new FlowLayout());
        usernameField.setPreferredSize(new Dimension(400, 50));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordField.setPreferredSize(new Dimension(400, 50));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel passwordAgainPanel = new JPanel(new FlowLayout());
        passwordAgainField.setPreferredSize(new Dimension(400, 50));
        passwordAgainPanel.add(passwordAgainLabel);
        passwordAgainPanel.add(passwordAgainField);

        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(passwordAgainPanel);
        this.add(buttonsPanel);
    }
}
