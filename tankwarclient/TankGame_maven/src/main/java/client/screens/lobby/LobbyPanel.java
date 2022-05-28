package client.screens.lobby;

import client.model.dto.Game;
import client.model.dto.Message;
import client.model.entity.Player;
import client.screens.waitingroom.WaitingRoomPanel;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;
import client.util.JsonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class LobbyPanel extends JPanel {
    JPanel parentPanel = new JPanel();

    //Panels
    JPanel buttonsPanel = new JPanel();
    JPanel gameRooms = new JPanel(new GridLayout(0,1));
    JPanel chat = new JPanel(new GridLayout(0,1));

    //Buttons
    JButton newGameButton = new JButton("New Game");
    JButton leadershipButton = new JButton("LeaderShip");
    JButton aboutUsButton = new JButton("About Us");
    JButton logoutButton = new JButton("Log Out");
    JButton refreshButton = new JButton("Refresh");


    //Labels
    JLabel gameTitleLabel = new JLabel("TankWars Games");

    //Games List
    List<Game> dummyGames = new ArrayList<>();
    Game dummyGame1 = Game.builder().tourNumber(1).mapType("a").shootingSpeed(1.5d).id(2).build();
    Game dummyGame2 = Game.builder().tourNumber(2).mapType("b").shootingSpeed(1d).id(3).build();Integer joinedGameRoomId;

    public LobbyPanel(JPanel parentPanel, Integer playerId) {
        this.parentPanel = parentPanel;
        GridLayout gridLayout = new GridLayout(4,2);
        setLayout(gridLayout);
        gridLayout.setHgap(20);
        gridLayout.setVgap(10);

        //open New Game
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO bu kısım bitti gibi?
                //Lobby ekranına gelen player datası içindeki playerIdyi newGamePanelına ilet
                NewGamePanel newGamePanel = new NewGamePanel(parentPanel,playerId);
                parentPanel.add(newGamePanel,"newGamePanel");

                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel,"newGamePanel");
            }
        });

        //open Leadership
        leadershipButton.addActionListener(new ActionListener() {
            //TODO bu kısım bitti gibi?
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
                //TODO statik sayfa Home'dan ulaşılabilir bitti gibi?
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel,"aboutUsPanel");
            }
        });

        //LogOut from game
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO perform LogOut
                ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
                Player player = Player.builder().id(playerId).build();
                cs.sendMessage("LT",player);

                if(cs.response().contains("OK")){
                    CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                    cardLayout.show(parentPanel, "loginPanel");
                }else {
                    System.out.println("LogOut response hatası");
                    //TODO GUI warning message
                    //JOptionPane.showMessageDialog(parentPanel,"LogOut Error");
                }

            }
        });

        //Refresh Lobby
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO refresh lobby panel, get active games
                ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
                cs.sendMessage("GU",null);
                System.out.println("SERVER RESPONSE (GU) "+cs.response());

                //dummyGames listesi
                dummyGames.add(dummyGame1);
                dummyGames.add(dummyGame2);

                if(cs.response().contains("OK")){
                    String gamesDataString = cs.response().substring(2);
                    gameRooms.removeAll();
                    //games.clear();
                    List <Game> games =  JsonUtil.fromListJson(gamesDataString, Game[].class);
                    System.out.println("games\n" + games);
                    //System.out.println(games.size());
                    //System.out.println(games.get(0).getId());
                    System.out.println();

                    for(Game game : games){
                        String gameInfoString = String.format("Tour:%d Speed:%.1f Map:%s",game.getTourNumber(),
                                game.getShootingSpeed(),game.getMapType());
                        Integer gameId = game.getId();
                        JLabel gameInfo = new JLabel(gameInfoString);
                        JButton joinButton = new JButton("JOİN"+gameId);
                        JPanel gameComponent = new JPanel(new FlowLayout());
                        gameComponent.setSize(new Dimension(150,30));
                        gameComponent.add(gameInfo);
                        gameComponent.add(joinButton);
                        joinButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //cs.sendMessage("JG",{ne yazacam aw}); --> gameId ve playerId gidecek
                                System.out.println(gameId);
                                WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel(parentPanel, playerId, gameId);
                                parentPanel.add(waitingRoomPanel, "waitingRoomPanel");

                                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                                cardLayout.show(parentPanel, "waitingRoomPanel");
                            }
                        });

                        gameRooms.add(gameComponent);
                    }

                }else {
                    System.out.println("No available game or response error");
                    //JOptionPane.showMessageDialog(parentPanel, "No game or Response error");
                }




                //Refreshing page
                 //revalidate();
                 //repaint();
                gameRooms.updateUI();

            }
        });

        gameRooms.setPreferredSize(new Dimension(300,200));
        gameRooms.setBackground(Color.ORANGE);

        chat.setBackground(Color.cyan);
        chat.setSize(new Dimension(200,200));
        JTextField chatInput = new JTextField("Type Message");
        chatInput.setSize(new Dimension(200,30));
        chatInput.setBackground(Color.yellow);
        chatInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()== KeyEvent.VK_ENTER){
                    ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
                    Message message = Message.builder().playerId(playerId).playerUserName("Burak").text(chatInput.getText()).build();
                    //cs.sendMessage("CM",message); //--> server tarafında eklenmemiş
                    System.out.println("CHAT INPUT "+chatInput.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        chat.add(chatInput);

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

        refreshButton.setBackground(Color.BLUE);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusable(false);

        gameTitleLabel.setFont(new Font("Verdana", Font.PLAIN,24));

        //Add buttons to the buttonsPanel
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(leadershipButton);
        buttonsPanel.add(aboutUsButton);
        buttonsPanel.add(logoutButton);
        buttonsPanel.add(refreshButton);

        this.add(gameTitleLabel);
        this.add(buttonsPanel);
        this.add(gameRooms);
        this.add(chat);
    }
}