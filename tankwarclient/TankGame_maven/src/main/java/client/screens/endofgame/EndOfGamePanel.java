package client.screens.endofgame;

import client.model.dto.Game;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndOfGamePanel extends JPanel {
    JPanel parentPanel;

    JLabel winner = new JLabel("Player won!");
    JLabel heading = new JLabel("Game Statistics");

    JTable scoreTable;

    JButton backButton = new JButton("Back to lobby");

    /*
    You need to pass the gameId in order to get the statistics for the desired game when creating this pane.
    There is an example commented out on the Home.java
     */
    public EndOfGamePanel(JPanel parentPanel, Integer gameId) {
        this.parentPanel = parentPanel;

        ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
        Game currentGame = Game.builder().id(gameId).build();
        cs.sendMessage("GG", currentGame);
        System.out.println(cs.response());

        String[] columnNames = { "Player", "Score"};

        String[][] data = {
                { "Enver", "1500",},
                { "Eda", "10"}
        };

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scoreTable = new JTable(data, columnNames);
        scoreTable.setBounds(30, 40, 20, 30);
        scoreTable.setEnabled(false);
        scoreTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(scoreTable);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO perform LogOut
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "lobbyPanel");
            }
        });

        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        winner.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        heading.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        this.add(winner);
        this.add(heading);
        this.add(scrollPane);
        this.add(backButton);
    }
}
