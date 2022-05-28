package test;

import server.model.dto.Bullet;
import server.model.entity.Player;
import server.model.enumerated.FaceOrientation;
import server.model.response.UpdateGameResponse;
import server.utilization.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestSerialize {
    public static void main(String[] args){
        List<Player> players = new ArrayList<>();
        List<Bullet> bullets = new ArrayList<>();
        players.add(new Player(1, "aaa", "1", false));
        players.add(new Player(2, "bbb", "1", true));
        bullets.add(new Bullet(1, UUID.randomUUID(), 100, 100, FaceOrientation.LEFT));
        bullets.add(new Bullet(2, UUID.randomUUID(), 20, 10, FaceOrientation.RIGHT));
        UpdateGameResponse response = UpdateGameResponse.builder()
                .bullets(bullets)
                .players(players)
                .build();
        System.out.println(JsonUtil.toJson(response));
        UpdateGameResponse a = JsonUtil.fromJson(JsonUtil.toJson(response), UpdateGameResponse.class);
        System.out.println(a);
    }
}
