package server.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.dao.PlayerDao;
import server.model.entity.Player;
import server.utilization.HashUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Setter
@RequiredArgsConstructor
public class PlayerService {

    private static final Integer CREATE_ERROR_RETURN_VALUE = -1;

    private static PlayerService playerService;

    private PlayerDao playerDao = PlayerDao.getInstance();

    public static PlayerService getInstance() {
        if (Objects.isNull(playerService)) {
            playerService = new PlayerService();
        }
        return playerService;
    }

    public Player createPlayer(Player player) {
        player.setPassword(HashUtil.hashValue(player.getPassword()));
        Integer newPlayerId = playerDao.createPlayer(player);
        if (newPlayerId.equals(CREATE_ERROR_RETURN_VALUE)) {
            System.out.println("Create player operation is failed");
            return null;
        }
        player.setId(newPlayerId);
        return player;
    }

    public Player getPlayer(Integer playerId) {
        return playerDao.getPlayer(playerId);
    }

    public List<Player> getActivePlayers() {
        return playerDao.getActivePlayers();
    }

    public boolean deletePlayer(Integer playerId) {
        return playerDao.deletePlayer(playerId);
    }

    public Player logout (Player player) {
        if (playerDao.updateActive(player.getId())) {
            return player;
        } else {
            return Player.builder().build();
        }
    }

    private boolean updatePlayerActivate(Integer playerId) {
        return playerDao.updateActive(playerId);
    }

    public Player login (Player player) {
        Player result = getDummyPlayer(player.getUsername());
        if (Objects.nonNull(result)) {
            if (result.getPassword().equals(HashUtil.hashValue(player.getPassword()))) {
                return Player.builder().id(result.getId()).username(result.getUsername()).build();
            }
        }
        return null;
    }

    private Player getPlayer(String username) {
        return playerDao.getPlayer(username);
    }

    private Player getDummyPlayer(String username) {
        if (username.toLowerCase(Locale.ROOT).equals("player1")) {
            return Player.builder().id(1).username("player1").password(HashUtil.hashValue("test")).isActive(Boolean.TRUE).build();
        } else {
            return Player.builder().id(2).username("player2").password(HashUtil.hashValue("test")).isActive(Boolean.TRUE).build();
        }
    }

    public List<Player> getDummyActivePlayers() {
        return Arrays.asList(
                Player.builder().id(1).username("player1").password(HashUtil.hashValue("test")).isActive(Boolean.TRUE).build(),
                Player.builder().id(2).username("player2").password(HashUtil.hashValue("test")).isActive(Boolean.TRUE).build());
    }

}
