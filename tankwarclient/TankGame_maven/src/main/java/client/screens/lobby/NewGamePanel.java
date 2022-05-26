package client.screens.lobby;

import client.game.CurrentPlayer;
import client.model.dto.Game;
import client.model.entity.Player;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGamePanel extends JPanel {

    JPanel parentPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    //Texts
    JLabel createGameLabel = new JLabel("Create New Game");
    JTextField roomNameField = new JTextField();
    String roomName;
    int selectedTour;
    float selectedShootSpeed;
    char selectedMapType;

    //ComboBoxes
    JComboBox tourNumber,mapType,shootSpeed;

    //ComboBox values
    final Integer tourNumberValues[] = {1,2,3};
    final Float shootSpeedValues[] = {1f, 1.5f, 2f};
    final Character[] mapTypeValues = {'a','b','c'};

    //Buttons
    JButton createGameButton = new JButton("Create Game Room");
    JButton cancelButton = new JButton("Cancel");

    public NewGamePanel(JPanel parentPanel, Integer playerId) {
        this.parentPanel = parentPanel;
        GridLayout gridLayout = new GridLayout(6,1);
        setLayout(gridLayout);
        gridLayout.setHgap(20);
        gridLayout.setVgap(10);

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
                selectedShootSpeed = (float) shootSpeed.getSelectedItem();
                selectedMapType = (char) mapType.getSelectedItem();

                //Player Id'si ile yeni oyun build et
                Game game = Game.builder().tourNumber(selectedTour).mapType(String.valueOf(selectedMapType)).shootingSpeed(selectedShootSpeed).id(playerId).build();
                ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
                cs.sendMessage("CG", game);
                if(cs.response().startsWith("OK")) {
                    System.out.println(cs.response());
                    // TODO navigate to game room
                } else {
                    // TODO show error message
                    //JOptionPane.showMessageDialog(parentPanel, "Couldn't create a new game");
                }
                // Remove these lines after filling if-else above
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "lobbyPanel");

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cancel creating new game room and return to the main page Lobby
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel,"lobbyPanel");
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

        this.add(createGameLabel);
        this.add(roomNameField);
        this.add(tourNumber);
        this.add(mapType);
        this.add(shootSpeed);
        this.add(buttonsPanel);

    }
}