package client.screens.lobby;

import client.model.dto.Game;
import client.rabbitmq.RPCClient;
import client.screens.waitingroom.WaitingRoomPanel;
import client.services.SingletonSocketService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGamePanel extends JPanel {

    JPanel parentPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JPanel contentPanel = new JPanel();

    //Texts
    JLabel createGameLabel = new JLabel("Create New Game");
    JTextField roomNameField = new JTextField("RoomName");
    JLabel tourNumberLabel = new JLabel("Select tour number");
    JLabel mapTypeLabel = new JLabel("Select map type");
    JLabel speedLabel = new JLabel("Select shoot speed");
    String roomName;
    int selectedTour;
    double selectedShootSpeed;
    char selectedMapType;

    //ComboBoxes
    JComboBox tourNumber, mapType, shootSpeed;

    //ComboBox values
    final Integer tourNumberValues[] = {1, 2, 3};
    final Double shootSpeedValues[] = {1d, 1.5d, 2d};
    final Character[] mapTypeValues = {'a', 'b', 'c'};

    //Buttons
    JButton createGameButton = new JButton("Create Game Room");
    JButton cancelButton = new JButton("Cancel");

    public NewGamePanel(JPanel parentPanel, Integer playerId) {
        this.parentPanel = parentPanel;
        GridLayout gridLayout = new GridLayout();
        setLayout(gridLayout);
        gridLayout.setHgap(20);
        gridLayout.setVgap(10);
        contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));

        tourNumber = new JComboBox(tourNumberValues);
        shootSpeed = new JComboBox(shootSpeedValues);
        mapType = new JComboBox(mapTypeValues);

        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO create gameRoom

                //get room name text from textField
                roomName = roomNameField.getText();

                //get comboBox values
                selectedTour = (int) tourNumber.getSelectedItem();
                selectedShootSpeed = (double) shootSpeed.getSelectedItem();
                selectedMapType = (char) mapType.getSelectedItem();

                //Player Id'si ile yeni oyun build et
                Game game = Game.builder().tourNumber(selectedTour).mapType(String.valueOf(selectedMapType)).shootingSpeed(selectedShootSpeed).id(playerId).build();
                RPCClient cs = SingletonSocketService.getInstance().rpcClient;
                cs.sendMessage("CG", game);
                if (cs.response().startsWith("OK")) {
                    System.out.println(cs.response());
                    // Navigate to game room
                    WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel(parentPanel, playerId, game.getId());
                    parentPanel.add(waitingRoomPanel, "waitingRoomPanel");
                    CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                    cardLayout.show(parentPanel, "waitingRoomPanel");
                } else {
                    JOptionPane.showMessageDialog(parentPanel, "Couldn't create a new game");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cancel creating new game room and return to the main page Lobby
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "lobbyPanel");
            }
        });

        //Customize
        createGameButton.setBackground(Color.BLUE);
        createGameButton.setForeground(Color.WHITE);
        createGameButton.setFocusable(false);

        cancelButton.setBackground(Color.ORANGE);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusable(false);

        buttonsPanel.add(createGameButton);
        buttonsPanel.add(cancelButton);

        createGameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        tourNumberLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mapTypeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        speedLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        roomNameField.setMinimumSize(new Dimension(450,40));
        roomNameField.setPreferredSize(new Dimension(450,40));
        roomNameField.setMaximumSize(new Dimension(450,40));

        tourNumber.setMinimumSize(new Dimension(450,40));
        tourNumber.setPreferredSize(new Dimension(450,40));
        tourNumber.setMaximumSize(new Dimension(450,40));

        mapType.setMinimumSize(new Dimension(450,40));
        mapType.setPreferredSize(new Dimension(450,40));
        mapType.setMaximumSize(new Dimension(450,40));

        shootSpeed.setMinimumSize(new Dimension(450,40));
        shootSpeed.setPreferredSize(new Dimension(450,40));
        shootSpeed.setMaximumSize(new Dimension(450,40));

        contentPanel.add(Box.createVerticalStrut(80));
        contentPanel.add(createGameLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(roomNameField);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(tourNumberLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(tourNumber);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(mapTypeLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(mapType);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(speedLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(shootSpeed);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(buttonsPanel);
        this.add(contentPanel);
    }
}