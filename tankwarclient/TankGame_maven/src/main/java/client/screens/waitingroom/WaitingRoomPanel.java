package client.screens.waitingroom;

import javax.swing.*;
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

    JPanel playersPanel = new JPanel(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
    JLabel waitingRoomTitle = new JLabel();
    JButton backToLobbyBtn = new JButton("Back to Lobby");

    public WaitingRoomPanel(JPanel parentPanel, Integer playerId, Integer gameId) {
        System.out.println("playerId: " + playerId);
        System.out.println("gameId: " + gameId);
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

        addToMainPanel();
        addToHeaderPanel(backToLobbyBtn, waitingRoomTitle);
        setWaitingRoomTitle();
    }

    private void addToMainPanel() {
        this.add(headerPanel);
    }

    private void addToHeaderPanel(JButton backButton, JLabel title) {
        headerPanel.add(backButton);
        headerPanel.add(Box.createHorizontalStrut(100));
        headerPanel.add(title);
    }

    private void setWaitingRoomTitle() {
        waitingRoomTitle.setText("Game-" + gameId + " Waiting Room");
        waitingRoomTitle.setFont(new Font("Verdana", Font.PLAIN, 24));
    }
}
