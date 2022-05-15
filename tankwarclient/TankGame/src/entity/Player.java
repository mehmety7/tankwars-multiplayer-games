package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;
    private long lastShot = -9999;

    boolean fire = false;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100 - gp.tileSize / 2;
        y = 300 - gp.tileSize / 2;
        direction = "up";
    }

    public void getPlayerImage() {
        try {
            up = ImageIO.read(new File("src/assets/player/blue_up.png"));
            down = ImageIO.read(new File("src/assets/player/blue_down.png"));
            left = ImageIO.read(new File("src/assets/player/blue_left.png"));
            right = ImageIO.read(new File("src/assets/player/blue_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(List<TestPlayer> testPlayers) {
//        System.out.println("X: " + x + "  Y: " + y);
        if (keyHandler.upPressed == true) {
            direction = "up";
            y -= speed;
        } else if (keyHandler.downPressed == true) {
            direction = "down";
            y += speed;
        } else if (keyHandler.leftPressed == true) {
            direction = "left";
            x -= speed;
        } else if (keyHandler.rightPressed == true) {
            direction = "right";
            x += speed;
        } else if (keyHandler.spacePressed == true) {
            if (shotFired()) {
                fire = true;
                checkFiringLine(testPlayers);
            }
        }
    }


    public boolean shotFired() {
        if (lastShot == -9999) {
            lastShot = System.currentTimeMillis();
            return true;
        } else {
            long currentTime = System.currentTimeMillis();
            int diff = (int) ((currentTime - lastShot) / 1000);

            if (diff >= fireTime) {
                lastShot = System.currentTimeMillis();
                return true;
            }
        }

        return false;
    }

    public void checkFiringLine(List<TestPlayer> testPlayers) {
        // TODO

        int barrelY = y;
        int barrelX = x;

        switch (direction) {
            case "up":
                for (TestPlayer tP : testPlayers) {
                    if ((tP.x + gp.tileSize / 2 >= barrelX && tP.x - gp.tileSize / 2 <= barrelX + 1) && tP.y <= barrelY) {
                        System.out.println("up hit");
                    }
                }
                break;
            case "down":
                for (TestPlayer tP : testPlayers) {
                    if ((tP.x + gp.tileSize / 2 >= barrelX && tP.x - gp.tileSize / 2 <= barrelX + 1) && tP.y >= barrelY) {
                        System.out.println("down hit");
                    }
                }
                break;
            case "left":
                for (TestPlayer tP : testPlayers) {
                    if ((tP.y + gp.tileSize / 2 >= barrelY && tP.y - gp.tileSize / 2 <= barrelY + 1) && tP.x <= barrelX) {
                        System.out.println("left hit");
                    }
                }
                break;
            case "right":
                for (TestPlayer tP : testPlayers) {
                    if ((tP.y + gp.tileSize / 2 >= barrelY && tP.y - gp.tileSize / 2 <= barrelY + 1) && tP.x >= barrelX) {
                        System.out.println("right hit");
                    }
                }
                break;
        }

    }


    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;

        switch (direction) {
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

        if(fire) {
            bulletTrack(g2);

            long currentTime = System.currentTimeMillis();
            long diff = (long) ((currentTime - lastShot) / 1000);


            if (diff >= fireTime/2 ) {
                fire = false;
            }
        }

    }


    public void bulletTrack(Graphics2D g2) {
        g2.setColor(Color.GREEN);

        switch (direction) {
            case "up":
                g2.drawLine(x+gp.tileSize/2, y+gp.tileSize/2, x+gp.tileSize/2, 0);
                break;
            case "down":
                g2.drawLine(x+gp.tileSize/2, y+gp.tileSize/2, x+gp.tileSize/2, gp.screenHeight);
                break;
            case "left":
                g2.drawLine(x+gp.tileSize/2, y+gp.tileSize/2, 0, y+gp.tileSize/2);
                break;
            case "right":
                g2.drawLine(x+gp.tileSize/2, y+gp.tileSize/2, gp.screenWidth, y+gp.tileSize/2);
                break;


        }

        g2.setColor(Color.WHITE);
    }
}
