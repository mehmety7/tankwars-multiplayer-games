package client.screens.waitingroom;

import client.model.dto.Game;
import client.model.dto.Tank;
import client.services.WaitingRoomService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomPanel extends JPanel {
    WaitingRoomService waitingRoomService;
    Timer t = new Timer();
    Game currentGame;
    List<Tank> tanks;
    JPanel parentPanel;
    Integer playerId;
    Integer gameId;
    JPanel headerPanel = new JPanel();
    JPanel bodyPanel = new JPanel();
    JPanel playersPanel = new JPanel();
    JPanel gameDetailPanel = new JPanel();
    JPanel gameParametersPanel = new JPanel();
    JLabel gameParametersTitle = new JLabel("Game Parameters", SwingConstants.CENTER);
    JLabel waitingRoomTitle = new JLabel();
    JLabel isStartStatusLabel = new JLabel();
    JButton backToLobbyBtn = new JButton("Back to Lobby");
    JButton startGameBtn = new JButton("Start the Game");

    public WaitingRoomPanel(JPanel parentPanel, Integer playerId, Integer gameId) {
        this.waitingRoomService = new WaitingRoomService();
        this.currentGame = waitingRoomService.getGame(gameId);

        if (gameId.equals(playerId)) {
            backToLobbyBtn.setText("Close the Room!");
        }


        this.parentPanel = parentPanel;
        this.playerId = playerId;
        this.gameId = gameId;
        this.setLayout(new GridLayout());

        backToLobbyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (waitingRoomService.returnToLobby(gameId, playerId)) {
                    t.cancel();
                    CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                    cardLayout.show(parentPanel, "lobbyPanel");
                }
            }
        });
        startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (waitingRoomService.isStartGame(gameId, playerId)) {
                    tanks = waitingRoomService.startGame(gameId);
                } else {
                    isStartStatusLabel.setText("You can not start the game!");
                }
            }
        });

        addToMainPanel();
        setWaitingRoomTitle();
        this.t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentGame = waitingRoomService.getGame(gameId);
                if (currentGame == null) {
                    t.cancel();
                    CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                    cardLayout.show(parentPanel, "lobbyPanel");
                }
                playersPanel.removeAll();
                addToPlayersPanel();
                playersPanel.revalidate();
                playersPanel.repaint();
            }
        }, 0, 1000);
    }

    private void addToMainPanel() {
        this.setLayout(new GridLayout(2, 1, 0, 0));
        this.add(headerPanel);
        this.add(bodyPanel);
        addToHeaderPanel();
        addToBodyPanel();
    }

    private void addToGameParametersPanel() {
        gameParametersPanel.setLayout(new BoxLayout(gameParametersPanel, BoxLayout.Y_AXIS));
        gameParametersPanel.setBackground(Color.YELLOW);
        gameParametersPanel.setMinimumSize(new Dimension(200, 200));
        gameParametersPanel.setPreferredSize(new Dimension(200, 200));
        gameParametersPanel.setMaximumSize(new Dimension(200, 200));
        gameParametersPanel.add(gameParametersTitle);
        gameParametersPanel.setBorder(new EmptyBorder(5, 50, 5, 0));
    }

    private void addToBodyPanel() {
        bodyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bodyPanel.add(playersPanel);
        bodyPanel.add(Box.createHorizontalStrut(75));
        bodyPanel.add(gameDetailPanel);
        bodyPanel.add(Box.createHorizontalStrut(75));
        bodyPanel.add(startGameBtn);
        startGameBtn.setMinimumSize(new Dimension(150, 50));
        startGameBtn.setPreferredSize(new Dimension(200, 65));
        startGameBtn.setMaximumSize(new Dimension(450, 150));
        addToGameDetailPanel();
    }

    private void addToGameDetailPanel() {
        gameDetailPanel.setLayout(new BoxLayout(gameDetailPanel, BoxLayout.Y_AXIS));
        gameDetailPanel.add(gameParametersPanel);
        gameDetailPanel.add(Box.createVerticalStrut(50));
        gameDetailPanel.add(isStartStatusLabel);
        addToGameParametersPanel();
    }

    private void addToPlayersPanel() {
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        List<String> players = getUsernames();
        System.out.println(players);
        for (String player : players) {
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
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        headerPanel.add(backToLobbyBtn);
        headerPanel.add(Box.createHorizontalStrut(600));
        headerPanel.add(waitingRoomTitle);
    }

    private void setWaitingRoomTitle() {
        waitingRoomTitle.setText("Game-" + gameId + " Waiting Room");
        waitingRoomTitle.setFont(new Font("Verdana", Font.PLAIN, 24));
    }

    private List<String> getUsernames() {
        Map<Integer, Integer> players = currentGame.getPlayers();
        List<Integer> playerIds = players.keySet().stream().toList();
        return this.waitingRoomService.getUsernames(playerIds);
    }
}
