package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestPlayer extends Entity{
    GamePanel gp;

    public TestPlayer(GamePanel gp, KeyHandler keyHandler, int defaulX) {
        this.gp = gp;
        setDefaultValues();
        getPlayerImage();
        x=defaulX;


    }

    public void setDefaultValues(){
        x = 300 - gp.tileSize/2;
        y = 300 - gp.tileSize/2;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            up = ImageIO.read(new File("src/assets/enemy/red_up.png"));
            down = ImageIO.read(new File("src/assets/enemy/red_down.png"));
            left = ImageIO.read(new File("src/assets/enemy/red_left.png"));
            right = ImageIO.read(new File("src/assets/enemy/red_right.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
//        x+=speed;


//        if(keyHandler.upPressed == true){
//            direction = "up";
//            y -= speed;
//        }else if(keyHandler.downPressed == true){
//            direction = "down";
//            y += speed;
//        }else if(keyHandler.leftPressed == true){
//            direction = "left";
//            x -= speed;
//        }else if(keyHandler.rightPressed == true){
//            direction = "right";
//            x += speed;
//        }
    }
    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;

        switch (direction){
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);


    }
}
