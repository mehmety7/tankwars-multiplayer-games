package test;


import server.dao.InMemoryDao;
import server.model.dto.Game;
import server.model.dto.Tank;
import server.model.entity.Player;
import server.model.enumerated.FaceOrientation;
import server.service.GameService;
import server.service.PlayerService;
import server.service.navigator.ServiceOperationNavigator;
import server.socket.Protocol;
import server.utilization.JsonUtil;

import java.util.HashMap;
import java.util.Map;


public class TestServices {

    public static void main(String[] args) {

        ServiceOperationNavigator serviceOperationNavigator = ServiceOperationNavigator.getInstance();
        GameService gameService = GameService.getInstance();
        gameService.createGame(Game.builder().id(1).build());
        gameService.joinGame(1,2);


        PlayerService playerService = PlayerService.getInstance();
        playerService.login(Player.builder().username("player1").password("test").build());
        System.out.println(playerService.getPlayer(1).getIsActive());
        playerService.logout(1);
        System.out.println(playerService.getPlayer(1).getIsActive());


        InMemoryDao inMemoryDao = InMemoryDao.getInstance();
        inMemoryDao.tanks.put(1, Tank.builder()
                .gameId(1)
                .playerId(1)
                .faceOrientation(FaceOrientation.RIGHT)
                .positionX(0)
                .positionY(0)
                .health(100)
                .build()
        );
        inMemoryDao.tanks.put(2, Tank.builder()
                .gameId(1)
                .playerId(2)
                .faceOrientation(FaceOrientation.LEFT)
                .positionX(100)
                .positionY(0)
                .health(100)
                .build()
        );
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 0);
        map.put(2, 0);
        inMemoryDao.games.put(1, Game.builder()
                .id(1)
                .isStarted(true)
                .players(map)
                .shootingSpeed(1.0)
                .tourNumber(1)
                .mapType("a")
                .build());

        System.out.println(inMemoryDao.games.get(1));
        Protocol protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        String response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        protocol = Protocol.builder().message(JsonUtil.toJson(inMemoryDao.tanks.get(1))).methodType("SF").build();
        response = serviceOperationNavigator.doOperation(protocol);
        System.out.println(response);
        System.out.println(inMemoryDao.tanks.get(2));
        System.out.println(inMemoryDao.games.get(1));
    }

}

