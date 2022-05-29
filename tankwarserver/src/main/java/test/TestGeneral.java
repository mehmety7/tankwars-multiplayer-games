package test;

import server.model.dto.Bullet;
import server.model.dto.Game;
import server.model.dto.Statistic;
import server.model.dto.Tank;
import server.model.entity.Player;
import server.model.enumerated.FaceOrientation;
import server.model.request.PlayerGameRequest;
import server.model.response.AboutUsResponse;
import server.model.response.UpdateGameResponse;
import server.service.navigator.ServiceOperationNavigator;
import server.socket.Protocol;
import server.utilization.JsonUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestGeneral {

    public static void main(String[] args) {

        HashMap<Integer, Integer> players = new HashMap<>();
        players.put(1,1);
        players.put(2,1);
        players.put(3,1);

        HashMap<Integer, Integer> players2 = new HashMap<>();
        players2.put(1,1);
        players2.put(2,1);
        players2.put(3,1);

        System.out.println("---------------------");
        System.out.println("Test with Game List");
        System.out.println("---------------------");

        Game game1 = Game.builder().id(1).players(players).shootingSpeed(5.0).tourNumber(2).mapType("a").build();
        Game game2 = Game.builder().id(2).players(players2).shootingSpeed(5.0).tourNumber(2).mapType("b").build();
        List<Game> games = Arrays.asList(game1, game2);

        String gamesJsonList = JsonUtil.toJson(games);
        System.out.println(gamesJsonList);

        List<Game> result = JsonUtil.fromListJson(gamesJsonList, Game[].class);
        System.out.println(result.get(0).getId());
        for (Game game : result) {
            System.out.println(game);
        }

        Protocol prot = new Protocol();
        prot.setMethodType("AG");
        String f = ServiceOperationNavigator.getInstance().doOperation(prot);
        System.out.println(f);


        System.out.println("---------------------");
        System.out.println("Test with Tank List");
        System.out.println("---------------------");

        Tank tank1 = Tank.builder().gameId(1).playerId(2).faceOrientation(FaceOrientation.DOWN).build();
        Tank tank2 = Tank.builder().gameId(1).playerId(3).faceOrientation(FaceOrientation.UP).build();
        List<Tank> tanks = Arrays.asList(tank1, tank2);

        String tanksJsonList = JsonUtil.toJson(tanks);
        System.out.println(tanks);

        List<Tank> result2 = JsonUtil.fromListJson(tanksJsonList, Tank[].class);
        System.out.println(result2.get(0).getPlayerId());
        for (Tank tank : result2) {
            System.out.println(tank);
        }

        System.out.println("---------------------");
        System.out.println("Update Game Response Json Operation Test");
        System.out.println("---------------------");

        Bullet bullet1 = Bullet.builder().tankId(1).positionX(10).positionY(10).faceOrientation(FaceOrientation.UP).build();
        Bullet bullet2 = Bullet.builder().tankId(2).positionX(10).positionY(10).faceOrientation(FaceOrientation.UP).build();
        List<Bullet> bullets = Arrays.asList(bullet1, bullet2);

        UpdateGameResponse updateGameResponse = new UpdateGameResponse(tanks, bullets);

        String uGResponseJsonList = JsonUtil.toJson(updateGameResponse);
        System.out.println(uGResponseJsonList);

        UpdateGameResponse deserializeUGR = JsonUtil.fromJson(uGResponseJsonList, UpdateGameResponse.class);
        System.out.println(deserializeUGR);


        System.out.println("---------------------");
        System.out.println("Old Trails");
        System.out.println("---------------------");
        System.out.println(JsonUtil.toJson(Game.builder().build()));
        System.out.println(JsonUtil.toJson(PlayerGameRequest.builder().build()));
        System.out.println(JsonUtil.toJson(Tank.builder().build()));
        System.out.println(JsonUtil.toJson(Statistic.builder().build()));
        System.out.println(JsonUtil.toJson(AboutUsResponse.text));
        System.out.println(JsonUtil.fromJson("{\"username\":\"test\",\"password\":\"pw\"}", Player.class));
    }

}
