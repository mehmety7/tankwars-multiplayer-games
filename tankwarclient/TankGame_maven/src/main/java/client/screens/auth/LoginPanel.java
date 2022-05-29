package client.screens.auth;

import client.model.entity.Player;
import client.screens.lobby.LobbyPanel;
import client.screens.waitingroom.WaitingRoomPanel;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;
import client.util.JsonUtil;

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
                Player player = Player.builder().username(usernameField.getText()).password(String.valueOf(passwordField.getPassword())).build();
                ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
                cs.sendMessage("LG", player);

                if (cs.response().contains("OK")) {
                    Player playerDataFromResponse;
                    String playerDataString = cs.response().substring(2);

                    playerDataFromResponse = JsonUtil.fromJson(playerDataString,Player.class);
                    LobbyPanel lobbyPanel = new LobbyPanel(parentPanel,playerDataFromResponse.getId(),playerDataFromResponse.getUsername());
                    parentPanel.add(lobbyPanel,"lobbyPanel");


                    CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                    cardLayout.show(parentPanel, "lobbyPanel");
                } else {
                    System.out.println("Wrong credentials");
                }

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

        JPanel usernamePanel = new JPanel(new FlowLayout());
        usernameField.setPreferredSize(new Dimension(400, 50));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordField.setPreferredSize(new Dimension(400, 50));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(buttonsPanel);
    }
}