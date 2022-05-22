package client.game;

import client.model.dto.Tank;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements  Runnable{

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


    public List<EnemyPlayer> enemyPlayers = new ArrayList<EnemyPlayer>();
    public CurrentPlayer currentPlayer = new CurrentPlayer(this, keyHandler);;



    public GamePanel(Integer currentPlayerId, List<Tank> tanks){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // to improve rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.currentPlayerId = currentPlayerId;
        this.tanks = tanks;

        startGameThread();
        createPlayers();


    }




//    TestPlayer testPlayer1 = new TestPlayer(this, new KeyHandler());
//    TestPlayer testPlayer2 = new TestPlayer(this, new KeyHandler());


    public void createPlayers(){
        for(int i=0; i<tanks.size();i++){
            if(tanks.get(i).getPlayerId() != currentPlayerId){
                enemyPlayers.add(new EnemyPlayer(this, tanks.get(i)));
            }else{
                currentPlayer.tank = tanks.get(i);
            }
        }
    }

    public void updateGameFromServer(){

        //todo socketten tanks i guncelle

        enemyPlayers.clear();

        if(!tanks.contains(currentPlayer.tank)){
            currentPlayer.isAlive = false;
        }

        for(int i=0; i<tanks.size();i++){
            if(tanks.get(i).getPlayerId() != currentPlayerId){
                tanks.get(i).setPositionX(tanks.get(i).getPositionX()+1);
                enemyPlayers.add(new EnemyPlayer(this, tanks.get(i)));
            }else{
                if(currentPlayer.isAlive){
                    //todo send server my position
                }

            }
        }
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0; //for fps

        while (gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint(); // Swing method
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
//                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update(){

        //todo serverdan gelen SF


//        for(EnemyPlayer eP : enemyPlayers){
//            eP.update();
//        }

        if(currentPlayer.isAlive){
            currentPlayer.update(enemyPlayers);
        }

        updateGameFromServer();



    }



    public void paintComponent(Graphics g){ //override JComponent
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        currentPlayer.draw(g2);
//        player.test(g2);
        for(EnemyPlayer eP : enemyPlayers){
            eP.draw(g2);
        }
        g2.dispose();
    }
}
