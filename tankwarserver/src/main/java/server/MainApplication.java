package server;

import server.model.dto.Game;
import server.model.dto.Statistic;
import server.model.dto.Tank;
import server.model.entity.Player;
import server.model.request.JoinGameRequest;
import server.model.response.AboutUsResponse;
import server.utilization.JsonUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainApplication {

    public static void main(String[] args) {
/*
        TankService tankService = new TankService();
        tankService.createOrUpdateTank(Tank.builder().playerId(1).build());
        Tank tank = tankService.getTank(1);

        String json = JsonUtil.toJson(tank);

        System.out.println(tank);
        System.out.println(JsonUtil.toJson(tank));
        System.out.println(JsonUtil.fromJson(json, Tank.class));
*/

        /*
        PlayerDao playerDao = PlayerDao.getInstance();

        System.out.println(playerDao.createPlayer(Player.builder().username("adam").password("smith").build()));

        List<Player> players = playerDao.getActivePlayers();
        System.out.println(players.get(0).getUsername());

        Player player = playerDao.getPlayer(2);
        System.out.println(player.getUsername());

        Player player1 = playerDao.getPlayer("adam");
        System.out.println(player1.getPassword());

        playerDao.updateActive(3);

        playerDao.deletePlayer(6);

        playerDao.getDataPersistence().getConnection().close();
        */

        String SPLITTER = "}" + ",";


        Game game1 = Game.builder().id(1).shootingSpeed(5.0).tourNumber(2).build();
        game1.setPlayers(new HashMap<>());
        game1.getPlayers().put(1, 5);
        Game game2 = Game.builder().id(2).shootingSpeed(5.0).tourNumber(2).build();


        String json = JsonUtil.toJson(game1);
        System.out.println(json);
        Game game = JsonUtil.fromJson(json, Game.class);
        System.out.println(JsonUtil.toJson(game));
        System.out.println(game.getPlayers().getClass());


        List<Game> games = Arrays.asList(game1, game2);

        String listJson = JsonUtil.toJson(games);

        List<Game> result = JsonUtil.fromListJson(listJson);


        System.out.println(result.get(0));
        System.out.println(result.get(1));




        System.out.println("---------------------");
        System.out.println(JsonUtil.toJson(Game.builder().build()));
        System.out.println(JsonUtil.toJson(JoinGameRequest.builder().build()));
        System.out.println(JsonUtil.toJson(Tank.builder().build()));
        System.out.println(JsonUtil.toJson(Statistic.builder().build()));
        System.out.println(JsonUtil.toJson(AboutUsResponse.text));
        System.out.println(JsonUtil.fromJson("{\"username\":\"test\",\"password\":\"pw\"}", Player.class));
    }

}
