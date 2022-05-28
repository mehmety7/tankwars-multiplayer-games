package client.screens.waitingroom;

import client.model.dto.Game;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class WaitingRoomPanel extends JPanel {
    JPanel parentPanel;
    Integer playerId;
    Integer gameId;
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

    JPanel bodyPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    JPanel playersPanel = new JPanel();
    JPanel gameDetailPanel = new JPanel();
    JLabel waitingRoomTitle = new JLabel();
    JButton backToLobbyBtn = new JButton("Back to Lobby");
    JButton startGameBtn = new JButton("Start the Game");

    public WaitingRoomPanel(JPanel parentPanel, Integer playerId, Integer gameId) {
        ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
        cs.sendMessage("GG", new Game(gameId, null, null, null, null, null));
        System.out.println("Server response: " + cs.response());

        this.parentPanel = parentPanel;
        this.playerId = playerId;
        this.gameId = gameId;
        this.setLayout(new GridLayout());

        backToLobbyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "lobbyPanel");
            }
        });
        startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        addToMainPanel();
        addToHeaderPanel();
        addToBodyPanel();
        setWaitingRoomTitle();
    }

    private void addToMainPanel() {
        this.setLayout(new GridLayout(2, 1));
        this.add(headerPanel);
        this.add(bodyPanel);
    }

    private void addToBodyPanel() {
        bodyPanel.add(playersPanel);
        bodyPanel.add(Box.createHorizontalStrut(75));
        bodyPanel.add(gameDetailPanel);
        addToPlayersPanel();
        addToGameDetailPanel();
    }

    private void addToGameDetailPanel() {
        gameDetailPanel.setLayout(new BoxLayout(gameDetailPanel, BoxLayout.Y_AXIS));
        gameDetailPanel.add(startGameBtn);
    }

    private void addToPlayersPanel() {
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        List<String> mockPlayers = new ArrayList<>(List.of("test1", "test2", "test3"));
        for (String player : mockPlayers) {
            JLabel playerLabel = new JLabel(player);
            playerLabel.setBackground(Color.BLUE);
            playerLabel.setOpaque(true);
            playerLabel.setForeground(Color.white);
            playerLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
            playerLabel.setMinimumSize(new Dimension(350, 30));
            playerLabel.setPreferredSize(new Dimension(350, 30));
            playerLabel.setMaximumSize(new Dimension(350, 30));
            playersPanel.add(playerLabel);
            playersPanel.add(Box.createVerticalStrut(5));
        }
    }

    private void addToHeaderPanel() {
        headerPanel.add(backToLobbyBtn);
        headerPanel.add(Box.createHorizontalStrut(100));
        headerPanel.add(waitingRoomTitle);
    }

    private void setWaitingRoomTitle() {
        waitingRoomTitle.setText("Game-" + gameId + " Waiting Room");
        waitingRoomTitle.setFont(new Font("Verdana", Font.PLAIN, 24));
    }
}
