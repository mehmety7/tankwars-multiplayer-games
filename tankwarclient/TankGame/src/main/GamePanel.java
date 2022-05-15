package main;

import entity.Player;
import entity.TestPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements  Runnable{

    // Screen Settings
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;


    final int playerCount;
    List<TestPlayer> testPlayers = new ArrayList<TestPlayer>();

    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    public GamePanel thisPanel = this;

    // TODO , baslangic konumu serverdan gelicek
    public Player player = new Player(this, keyHandler);;

    Thread gameThread;

    public GamePanel(int playerCount){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // to improve rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        startGameThread();
        this.playerCount = playerCount-1;
        createPlayers();


    }




//    TestPlayer testPlayer1 = new TestPlayer(this, new KeyHandler());
//    TestPlayer testPlayer2 = new TestPlayer(this, new KeyHandler());


    public void createPlayers(){
        // TODO , bu konumlar serverdan gelicek
        int defaultX = 200;
        for(int i=0; i<this.playerCount;i++){
            testPlayers.add(new TestPlayer(this, new KeyHandler(), defaultX));
            defaultX+=100;
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

        for(TestPlayer tP : testPlayers){
            tP.update();
        }

        player.update(testPlayers);

    }



    public void paintComponent(Graphics g){ //override JComponent
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
//        player.test(g2);
        for(TestPlayer tP : testPlayers){
            tP.draw(g2);
        }
        g2.dispose();
    }
}
