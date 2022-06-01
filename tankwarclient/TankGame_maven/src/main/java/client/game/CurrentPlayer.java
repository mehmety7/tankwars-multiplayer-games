package client.game;


import client.model.dto.Tank;
import client.model.enumerated.FaceOrientation;
import client.rabbitmq.RPCClient;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentPlayer {
    public static String assetPath = "C:/Users/HUZEYFE/Desktop/tankwars/tankwarclient/TankGame_maven/src/main/java/client/assets/player/";
    public Tank tank;
    private Integer speed = 2;
    private BufferedImage up, down, left, right;
    private Integer shootingSpeed = 1;
    private GamePanel gp;
    private KeyHandler keyHandler;
    private long lastShot = -9999;
    private boolean fire = false;

    public boolean isAlive = true;

    public CurrentPlayer(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up = ImageIO.read(new File(readFileFromResourcesAsString("blue_up.png")));
            down = ImageIO.read(new File(readFileFromResourcesAsString("blue_down.png")));
            left = ImageIO.read(new File(readFileFromResourcesAsString("blue_left.png")));
            right = ImageIO.read(new File(readFileFromResourcesAsString("blue_right.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
//        System.out.println("X: " + x + "  Y: " + y);
        if (keyHandler.upPressed == true) {
            tank.setFaceOrientation(FaceOrientation.UP);
            if (tank.getPositionY() >= 0) {
                tank.setPositionY(tank.getPositionY() - speed);
            }
        } else if (keyHandler.downPressed == true) {
            tank.setFaceOrientation(FaceOrientation.DOWN);
            if (tank.getPositionY() + gp.tankSize <= gp.screenHeight) {
                tank.setPositionY(tank.getPositionY() + speed);
                System.out.println("Asagi tiklandi");
            }
        } else if (keyHandler.leftPressed == true) {
            tank.setFaceOrientation(FaceOrientation.LEFT);
            if (tank.getPositionX() >= 0) {
                tank.setPositionX(tank.getPositionX() - speed);
            }
        } else if (keyHandler.rightPressed == true) {
            tank.setFaceOrientation(FaceOrientation.RIGHT);
            if (tank.getPositionX() + gp.tankSize <= gp.screenWidth) {
                tank.setPositionX(tank.getPositionX() + speed);
            }
        } else if (keyHandler.spacePressed == true) {

            if (shotFired()) {

                fire = true;

//                checkFiringLine(enemyPlayers);

                //TODO send bullet to server

                RPCClient cs = SingletonSocketService.getInstance().rpcClient;
                cs.sendMessage("SF", this.tank);
                System.out.println(cs.response());

                if (cs.response().contains("OK")) {
                    System.out.println("Enemy hit");
                } else {
                    System.out.println("Shot missed");
                }

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

            if (diff >= shootingSpeed) {
                lastShot = System.currentTimeMillis();
                return true;
            }
        }
        return false;
    }

    //todo public void checkFiringLine
    public void checkFiringLine(List<EnemyPlayer> enemyPlayers) {
        // TODO

        int barrelY = tank.getPositionY();
        int barrelX = tank.getPositionX();

        switch (tank.getFaceOrientation()) {
            case UP:
                for (EnemyPlayer eP : enemyPlayers) {
                    if ((eP.tank.getPositionX() + gp.tankSize / 2 >= barrelX && eP.tank.getPositionX() - gp.tankSize / 2 <= barrelX + 1) && eP.tank.getPositionY() <= barrelY) {
                        System.out.println("up hit");
                    }
                }
                break;
            case DOWN:
                for (EnemyPlayer eP : enemyPlayers) {
                    if ((eP.tank.getPositionX() + gp.tankSize / 2 >= barrelX && eP.tank.getPositionX() - gp.tankSize / 2 <= barrelX + 1) && eP.tank.getPositionY() >= barrelY) {
                        System.out.println("down hit");
                    }
                }
                break;
            case LEFT:
                for (EnemyPlayer eP : enemyPlayers) {
                    if ((eP.tank.getPositionY() + gp.tankSize / 2 >= barrelY && eP.tank.getPositionY() - gp.tankSize / 2 <= barrelY + 1) && eP.tank.getPositionX() <= barrelX) {
                        System.out.println("left hit");
                    }
                }
                break;
            case RIGHT:
                for (EnemyPlayer eP : enemyPlayers) {
                    if ((eP.tank.getPositionY() + gp.tankSize / 2 >= barrelY && eP.tank.getPositionY() - gp.tankSize / 2 <= barrelY + 1) && eP.tank.getPositionX() >= barrelX) {
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

        switch (tank.getFaceOrientation()) {
            case UP:
                image = up;
                break;
            case DOWN:
                image = down;
                break;
            case LEFT:
                image = left;
                break;
            case RIGHT:
                image = right;
                break;
        }

        g2.drawImage(image, tank.getPositionX(), tank.getPositionY(), gp.tankSize, gp.tankSize, null);

        if (fire) {
            //TODO draw bullet
            bulletTrack(g2);

            long currentTime = System.currentTimeMillis();
            long diff = (long) ((currentTime - lastShot) / 1000);


            if (diff >= shootingSpeed / 2) {
                fire = false;
            }
        }

    }

    public void bulletTrack(Graphics2D g2) {
        g2.setColor(Color.GREEN);

        switch (tank.getFaceOrientation()) {
            case UP:
                g2.drawLine(tank.getPositionX() + gp.tankSize / 2, tank.getPositionY() + gp.tankSize / 2, tank.getPositionX() + gp.tankSize / 2, 0);
                break;
            case DOWN:
                g2.drawLine(tank.getPositionX() + gp.tankSize / 2, tank.getPositionY() + gp.tankSize / 2, tank.getPositionX() + gp.tankSize / 2, gp.screenHeight);
                break;
            case LEFT:
                g2.drawLine(tank.getPositionX() + gp.tankSize / 2, tank.getPositionY() + gp.tankSize / 2, 0, tank.getPositionY() + gp.tankSize / 2);
                break;
            case RIGHT:
                g2.drawLine(tank.getPositionX() + gp.tankSize / 2, tank.getPositionY() + gp.tankSize / 2, gp.screenWidth, tank.getPositionY() + gp.tankSize / 2);
                break;


        }

        g2.setColor(Color.WHITE);
    }

    private String readFileFromResourcesAsString(String fileName) throws IOException {
        Path filePath = Paths.get("./src/main/resources/player/" + fileName);
        return filePath.toString();
    }

}
