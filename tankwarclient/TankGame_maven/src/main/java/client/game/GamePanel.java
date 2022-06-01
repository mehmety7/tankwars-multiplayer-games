package client.game;

import client.model.dto.Bullet;
import client.model.dto.Tank;
import client.model.request.UpdateGameRequest;
import client.model.response.UpdateGameResponse;
import client.rabbitmq.RPCClient;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;
import client.util.JsonUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tankSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = tankSize * maxScreenCol;
    public final int screenHeight = tankSize * maxScreenRow;


    int FPS = 60;


    Integer currentPlayerId;

    KeyHandler keyHandler = new KeyHandler();
    public GamePanel thisPanel = this;

    Thread gameThread;

    //todo =================================================================
    List<Tank> tanks;
    List<Bullet> bullets;

    public List<EnemyPlayer> enemyPlayers = new ArrayList<EnemyPlayer>();
    public CurrentPlayer currentPlayer = new CurrentPlayer(this, keyHandler);
    ;


    public GamePanel(Integer currentPlayerId, List<Tank> tanks) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // to improve rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.currentPlayerId = currentPlayerId;
        this.tanks = tanks;

        createPlayers();
        startGameThread();


    }


//    TestPlayer testPlayer1 = new TestPlayer(this, new KeyHandler());
//    TestPlayer testPlayer2 = new TestPlayer(this, new KeyHandler());


    public void createPlayers() {

        for (int i = 0; i < tanks.size(); i++) {
            if (tanks.get(i).getPlayerId() != currentPlayerId) {
                enemyPlayers.add(new EnemyPlayer(this, tanks.get(i)));
            } else {
                currentPlayer.tank = tanks.get(i);
            }
        }
    }

    public void updateGameFromServer() {
        RPCClient cs = SingletonSocketService.getInstance().rpcClient;
        UpdateGameRequest updateGameRequest = new UpdateGameRequest();
        updateGameRequest.setGameId(currentPlayer.tank.getGameId());
        cs.sendMessage("UG", updateGameRequest);
        System.out.println("Update Game Response: " + cs.response());

        if (cs.response().contains("OK")) {
            UpdateGameResponse updateGameResponse;
            String playerDataString = cs.response().substring(2);
            updateGameResponse = JsonUtil.fromJson(playerDataString, UpdateGameResponse.class);
            tanks = updateGameResponse.getTanks();
            bullets = updateGameResponse.getBullets();
            if (bullets.size() != 0) {
                Bullet temp = bullets.get(bullets.size() - 1);
                bullets.clear();
                bullets.add(temp);
            }
        } else {
            System.out.println("UG  failed");
        }

        enemyPlayers.clear();

//        if(!tanks.contains(currentPlayer.tank)){//todo bu calisir mi?
//            currentPlayer.isAlive = false;
//        }

        for (int i = 0; i < tanks.size(); i++) {
            if (tanks.get(i).getPlayerId() != currentPlayerId) {
                tanks.get(i).setPositionX(tanks.get(i).getPositionX() + 1);
                // System.out.println("Add enemy player called!");
                enemyPlayers.add(new EnemyPlayer(this, tanks.get(i)));
            } else {
                if (tanks.get(i).getHealth().equals(0)) {
                    currentPlayer.isAlive = false;
                }

            }
        }
    }

    public void bulletTrackFromServer(Graphics2D g2, Bullet bullet) {
        g2.setColor(Color.GREEN);

        switch (bullet.getFaceOrientation()) {
            case UP:
                g2.drawLine(bullet.getPositionX() + tankSize / 2, bullet.getPositionY() + tankSize / 2, bullet.getPositionX() + tankSize / 2, 0);
                break;
            case DOWN:
                g2.drawLine(bullet.getPositionX() + tankSize / 2, bullet.getPositionY() + tankSize / 2, bullet.getPositionX() + tankSize / 2, screenHeight);
                break;
            case LEFT:
                g2.drawLine(bullet.getPositionX() + tankSize / 2, bullet.getPositionY() + tankSize / 2, 0, bullet.getPositionY() + tankSize / 2);
                break;
            case RIGHT:
                g2.drawLine(bullet.getPositionX() + tankSize / 2, bullet.getPositionY() + tankSize / 2, screenWidth, bullet.getPositionY() + tankSize / 2);
                break;


        }

        g2.setColor(Color.WHITE);
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0; //for fps

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                this.removeAll();
                update();
                revalidate();
                repaint(); // Swing method
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
//                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        updateGameFromServer();

        if (currentPlayer.isAlive) {
            currentPlayer.update();

            RPCClient cs = SingletonSocketService.getInstance().rpcClient;
            cs.sendMessage("UD", currentPlayer.tank);

            // System.out.println("UpdateDataRequest:" + currentPlayer.tank);

            if (cs.response().contains("OK")) {

            } else {
                System.out.println("UD failed");
            }
        }


    }


    public void paintComponent(Graphics g) { //override JComponent
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        currentPlayer.draw(g2);
//        player.test(g2);
        for (EnemyPlayer eP : enemyPlayers) {
            eP.draw(g2);
        }

        if (bullets != null) {
            for (Bullet bullet : bullets) {
                bulletTrackFromServer(g2, bullet);
            }
            bullets.clear();
        }


        g2.dispose();
    }
}
