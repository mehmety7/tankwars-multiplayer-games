package client.screens.lobby;

import client.model.dto.Game;
import client.model.entity.Player;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;
import client.util.JsonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LobbyPanel extends JPanel {
    JPanel parentPanel = new JPanel();

    JPanel buttonsPanel = new JPanel();
    JPanel gameRooms = new JPanel(new GridLayout(0,1));

    //Buttons
    JButton newGameButton = new JButton("New Game");
    JButton leadershipButton = new JButton("LeaderShip");
    JButton aboutUsButton = new JButton("About Us");
    JButton logoutButton = new JButton("Log Out");
    JButton refreshButton = new JButton("Refresh");


    //Labels
    JLabel gameTitleLabel = new JLabel("TankWars Games");

    //Games List
    List<Game> games = new ArrayList<>();
    List<Game> dummyGames = new ArrayList<>();
    Game dummyGame1 = Game.builder().tourNumber(1).mapType("a").shootingSpeed(1.5f).id(2).build();
    Game dummyGame2 = Game.builder().tourNumber(2).mapType("b").shootingSpeed(1f).id(3).build();Integer joinedGameRoomId;

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

                //client socket data istiyor sendMessage overload edildi
                cs.sendMessage("GU",null);
                System.out.println(cs.response());

                //dummyGames listesi
                dummyGames.add(dummyGame1);
                dummyGames.add(dummyGame2);

                if(cs.response().contains("OK")){
                    String gamesDataString = cs.response().substring(2);
                    games.clear();
                    List<Game> games =  JsonUtil.fromListJson(gamesDataString);
                }else {
                    System.out.println("No available game or response error");
                    //JOptionPane.showMessageDialog(parentPanel, "No game or Response error");
                }

                for(int i=0;i<games.size();i++){
                    String gameInfoString = String.format("Tour:%d Speed:%.1f Map:%s",games.get(i).getTourNumber(),
                            games.get(i).getShootingSpeed(),games.get(i).getMapType());
                    Integer gameId = games.get(i).getId();
                    JLabel gameInfo = new JLabel(gameInfoString);
                    JButton joinButton = new JButton("JOİN"+gameId);//bu niye ekrana gelmedi aw
                    JPanel gameComponent = new JPanel(new FlowLayout());
                    gameComponent.setSize(new Dimension(150,30));
                    gameComponent.add(gameInfo);
                    gameComponent.add(joinButton);
                    joinButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //cs.sendMessage("JG",{ne yazacam aw}); --> gameId ve playerId gidecek
                            System.out.println(gameId);
                        }
                    });

                    gameRooms.add(gameComponent);
                }


                //Refreshing page
                 revalidate();
                 repaint();


            }
        });

        gameRooms.setPreferredSize(new Dimension(300,200));
        gameRooms.setBackground(Color.ORANGE);

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
    }
}