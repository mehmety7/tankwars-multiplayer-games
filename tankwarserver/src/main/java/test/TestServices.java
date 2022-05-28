package test;


import server.model.dto.Game;
import server.service.GameService;
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
        System.out.println(response);

    }

}

