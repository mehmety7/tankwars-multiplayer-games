import client.game.GamePanel;
import client.model.dto.Tank;
import client.model.enumerated.FaceOrientation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Tank Wars");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Tank Game");


        //todo  ====================================


        List<Tank> tanks = new ArrayList<Tank>();

        tanks.add(Tank.builder()
                .playerId(1)
                .gameId(1)
                .faceOrientation(FaceOrientation.UP)
                .health(10)
                .positionX(100)
                .positionY(100)
                .build());

        tanks.add(Tank.builder()
                .playerId(5)
                .gameId(1)
                .faceOrientation(FaceOrientation.DOWN)
                .health(10)
                .positionX(200)
                .positionY(200)
                .build());

        tanks.add(Tank.builder()
                .playerId(6)
                .gameId(1)
                .faceOrientation(FaceOrientation.DOWN)
                .health(10)
                .positionX(300)
                .positionY(300)
                .build());

        GamePanel gamePanel = new GamePanel(1, tanks);


        //todo  ====================================


        window.add(gamePanel);

        window.pack(); //fit

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
