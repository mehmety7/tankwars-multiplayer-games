package client.services;

import client.model.dto.Game;
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

    public boolean isStartGame(Integer gameId) {
        cs.sendMessage("SG", new Game(gameId, null, null, null, null, null));
        String responseStatus = cs.response().substring(0, 2);
        if (responseStatus.equals("FL"))
            return false;
        return true;
    }

    public void startGame(Integer gameId) {
    }
}
