package client.services;

import client.model.dto.Game;
import client.model.dto.Tank;
import client.model.entity.Player;
import client.screens.waitingroom.WaitingRoomPanel;
import client.socket.ClientSocket;
import client.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoomService {
    ClientSocket cs = SingletonSocketService.getInstance().clientSocket;

    public Game getGame(Integer gameId) {
        cs.sendMessage("GG", new Game(gameId, null, null, null, null, null));
        System.out.println("Server response: " + cs.response());
        Game game = JsonUtil.fromJson(cs.response().substring(2), Game.class);
        return game;
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
        System.out.println("Response Status: " + cs.response());
        String responseStatus = cs.response().substring(0, 2);
        if (responseStatus.equals("FL") || !gameId.equals(playerId))
            return false;
        return true;
    }

    public void startGame(Integer gameId) {
        cs.sendMessage("SG", Game.builder().id(gameId).build());
        String response = cs.response().substring(2);
        List<Tank> tanks = JsonUtil.fromListJson(response, Tank[].class);
        System.out.println("tanks: " + tanks);
    }
}
