package test;


import server.bean.BeanHandler;
import server.dao.InMemoryDao;
import server.model.dto.Game;
import server.model.dto.Tank;
import server.model.entity.Player;
import server.service.GameService;
import server.service.PlayerService;
import server.service.navigator.ServiceOperationNavigator;
import server.socket.Protocol;
import server.utilization.JsonUtil;


public class TestServices {

    public static void main(String[] args) {

        ServiceOperationNavigator serviceOperationNavigator = ServiceOperationNavigator.getInstance();
        GameService gameService = GameService.getInstance();
        gameService.createGame(Game.builder().id(1).build());
        gameService.joinGame(1,2);
        Protocol protocol = Protocol.builder().message(JsonUtil.toJson(Game.builder().id(1).build())).methodType("SG").build();
        String response = serviceOperationNavigator.doOperation(protocol);


        PlayerService playerService = PlayerService.getInstance();
        playerService.login(Player.builder().username("player1").password("test").build());
        System.out.println(playerService.getPlayer(1).getIsActive());
        playerService.logout(1);
        System.out.println(playerService.getPlayer(1).getIsActive());


        InMemoryDao inMemoryDao = InMemoryDao.getInstance();
        inMemoryDao.tanks.put(1, Tank.builder().playerId(1).build());
        inMemoryDao.tanks.put(1, Tank.builder().playerId(2).build());
        inMemoryDao.tanks.put(1, Tank.builder().playerId(8).build());
        inMemoryDao.tanks.put(1, Tank.builder().playerId(7).build());
        System.out.println(inMemoryDao.tanks.get(1));

        System.out.println(BeanHandler.playerService.testPlayers.size());

    }

}

