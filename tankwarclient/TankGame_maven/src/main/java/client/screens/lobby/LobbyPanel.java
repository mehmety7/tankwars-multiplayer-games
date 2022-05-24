package client.screens.lobby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyPanel extends JPanel {
    JPanel parentPanel = new JPanel();

    JPanel buttonsPanel = new JPanel();

    //Buttons
    JButton newGameButton = new JButton("New Game");
    JButton leadershipButton = new JButton("LeaderShip");
    JButton aboutUsButton = new JButton("About Us");
    JButton logoutButton = new JButton("Log Out");

    //Labels
    JLabel gameTitleLabel = new JLabel("TankWars Games");

    public LobbyPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
        GridLayout gridLayout = new GridLayout(4,2);
        setLayout(gridLayout);
        gridLayout.setHgap(20);
        gridLayout.setVgap(10);

        //open New Game
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO create newGamePanel
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel,"newGamePanel");
            }
        });

        //open Leadership
        leadershipButton.addActionListener(new ActionListener() {
            //TODO create leadershipPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel,"leadershipPanel");
            }
        });

        //open About Us
        aboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO create aboutUsPanel
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel,"aboutUsPanel");
            }
        });

        //LogOut from game
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO perform LogOut
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "loginPanel");
            }
        });

        //Customize Buttons
        newGameButton.setBackground(Color.BLUE);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFocusable(false);

        leadershipButton.setBackground(Color.BLUE);
        leadershipButton.setForeground(Color.WHITE);
        leadershipButton.setFocusable(false);

        aboutUsButton.setBackground(Color.BLUE);
        aboutUsButton.setForeground(Color.WHITE);
        aboutUsButton.setFocusable(false);

        logoutButton.setBackground(Color.BLUE);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusable(false);

        gameTitleLabel.setFont(new Font("Verdana", Font.PLAIN,24));

        //Add buttons to the buttonsPanel
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(leadershipButton);
        buttonsPanel.add(aboutUsButton);
        buttonsPanel.add(logoutButton);

        this.add(gameTitleLabel);
        this.add(buttonsPanel);

    }
}