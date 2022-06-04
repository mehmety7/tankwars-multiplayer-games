package client.services;

import client.model.dto.Game;
import client.model.dto.Tank;
import client.model.entity.Player;
import client.model.request.PlayerGameRequest;
import client.rabbitmq.RPCClient;
import client.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoomService {
    RPCClient cs = SingletonSocketService.getInstance().rpcClient;

    public Game getGame(Integer gameId) {
        cs.sendMessage("GG", Game.builder().id(gameId).build());
        if (cs.response().equals("FL"))
            return null;
        Game game = JsonUtil.fromJson(cs.response().substring(2), Game.class);
        return game;
    }

    public boolean returnToLobby(Integer gameId, Integer playerId) {
        cs.sendMessage("RL", PlayerGameRequest.builder().gameId(gameId).playerId(playerId).build());
        String responseStatus = cs.response();
        System.out.println("Return to lobby response: " + responseStatus);
        if (responseStatus.equals("OK")) return true;
        return false;
    }

    public List<String> getUsernames(List<Integer> playerIds) {
        List<String> usernames = new ArrayList<>();
        for (Integer playerId : playerIds) {
            cs.sendMessage("GP", new Player(playerId, null, null, null));
            Player responsePlayer = JsonUtil.fromJson(cs.response().substring(2), Player.class);
            usernames.add(responsePlayer.getUsername());
        }
        return usernames;
    }

    public boolean isStartGame(Integer gameId, Integer playerId) {
        cs.sendMessage("SG", Game.builder().id(gameId).build());
        // System.out.println("Response Status: " + cs.response());
        String responseStatus = cs.response().substring(0, 2);
        if (responseStatus.equals("FL") || !gameId.equals(playerId))
            return false;
        return true;
    }

    public List<Tank> startGame(Integer gameId) {
        cs.sendMessage("SG", Game.builder().id(gameId).build());
        String response = cs.response().substring(2);
        System.out.println("Response Start Game: " + cs.response());
        List<Tank> tanks = JsonUtil.fromListJson(response, Tank[].class);
        return tanks;
    }
}
