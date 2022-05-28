package client.screens.endofgame;

import client.model.dto.Game;
import client.model.dto.Statistic;
import client.model.entity.Player;
import client.services.SingletonSocketService;
import client.services.WaitingRoomService;
import client.socket.ClientSocket;
import client.util.JsonUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndOfGamePanel extends JPanel {
    JPanel parentPanel;
    WaitingRoomService waitingRoomService;
    Game currentGame;
    JLabel heading = new JLabel("Game Statistics");
    JTable scoreTable = new JTable();
    String[] columnNames = { "Player", "Score"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JScrollPane scrollPane;


    JButton backButton = new JButton("Back to lobby");

    List<Integer> scores;
    List<String> usernames;

    /*
    You need to pass the gameId in order to get the statistics for the desired game when creating this pane.
    There is an example commented out on the Home.java
     */
    public EndOfGamePanel(JPanel parentPanel, Integer gameId) {
        this.waitingRoomService = new WaitingRoomService();

        this.parentPanel = parentPanel;

        ClientSocket cs = SingletonSocketService.getInstance().clientSocket;

        this.currentGame = waitingRoomService.getGame(gameId);

        usernames = getUsernames();
        scores = getScores();

        //code to delete later
        //usernames.add("Eda");
        //scores.add(5);

        for (int i = 0; i < usernames.size(); i++) {
            model.addRow(new Object[] { String.valueOf(usernames.get(i)), String.valueOf(usernames.get(i)) });
        }


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //model.setColumnIdentifiers(columnNames);
        scoreTable.setModel(model);
        scrollPane = new JScrollPane(scoreTable);

        scoreTable.setBounds(30, 40, 20, 30);
        scoreTable.setEnabled(false);
        scoreTable.getTableHeader().setReorderingAllowed(false);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "lobbyPanel");
            }
        });

        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        heading.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        this.add(heading);
        this.add(scrollPane);
        this.add(backButton);
    }

    private List<String> getUsernames() {
        Map<Integer, Integer> players = currentGame.getPlayers();
        List<Integer> playerIds = players.keySet().stream().toList();
        return this.waitingRoomService.getUsernames(playerIds);
    }

    private List<Integer> getScores() {
        Map<Integer, Integer> players = currentGame.getPlayers();
        List<Integer> scores = players.values().stream().toList();
        return scores;
    }


}
