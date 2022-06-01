package server.service;

import lombok.Getter;
import lombok.Setter;
import server.bean.BeanHandler;
import server.dao.PlayerDao;
import server.model.entity.Player;
import server.utilization.HashUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class PlayerService {

    private static final Integer CREATE_ERROR_RETURN_VALUE = -1;

    private static PlayerService playerService;

    private final PlayerDao playerDao;

    public List<Player> testPlayers = new ArrayList<>();

    public static PlayerService getInstance() {
        if (Objects.isNull(playerService)) {
            playerService = new PlayerService(BeanHandler.playerDao);
        }
        return playerService;
    }

    private PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
        createDummyPlayers();
    }

    public Player getPlayer(Integer playerId) {
        return getDummyPlayer(playerId);
        // return playerDao.getPlayer(playerId);
    }

    private Player getPlayer(String username) {
        return getDummyPlayer(username);
        // return playerDao.getPlayer(username);
    }

    public List<Player> getActivePlayers() {
        return getDummyActivePlayers();
        // return playerDao.getActivePlayers();
    }

    public Player login (Player player) {
        Player result = getPlayer(player.getUsername());
        if (Objects.nonNull(result)) {
            if (result.getPassword().equals(HashUtil.hashValue(player.getPassword()))) {
                result.setIsActive(Boolean.TRUE);
                testPlayers.set(result.getId()-1, result);
                return Player.builder().id(result.getId()).username(result.getUsername()).isActive(result.getIsActive()).build();
            }
        }
        return null;
    }

    public Boolean logout (Integer playerId) {
        /* if (Boolean.TRUE.equals(playerDao.updateActive(player.getId()))) {
            return Boolean.TRUE;
        } */

        Player result = getPlayer(playerId);

        if (Objects.nonNull(result)) {
            result.setIsActive(Boolean.FALSE);
            testPlayers.set(result.getId()-1, result);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    public Player createPlayer(Player player) {

        return createDummyPlayer(player);
/*
        player.setPassword(HashUtil.hashValue(player.getPassword()));
        player.setIsActive(Boolean.FALSE);
        //Integer newPlayerId = playerDao.createPlayer(player);
        Integer newPlayerId = 0;
        if (newPlayerId.equals(CREATE_ERROR_RETURN_VALUE)) {
            System.out.println("Create player operation is failed");
            return null;
        }
        player.setId(newPlayerId);
        return player;
 */
    }

    private Player createDummyPlayer(Player player) {
        player.setId(testPlayers.size() + 1);
        player.setIsActive(Boolean.FALSE);
        String hashedPassword = HashUtil.hashValue(player.getPassword());
        player.setPassword(hashedPassword);
        testPlayers.add(player);
        return player;
    }

    private Player getDummyPlayer(String username) {
        for (Player player : testPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    private Player getDummyPlayer(Integer id) {
        for (Player player : testPlayers) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getDummyActivePlayers() {
        List<Player> response = new ArrayList<>();

        for (Player player : testPlayers) {
            if (player.getIsActive()) {
                response.add(player);
            }
        }

        return response;
    }

    private boolean updatePlayerActivate(Integer playerId) {
        // return playerDao.updateActive(playerId);
        return true;
    }

    public boolean deletePlayer(Integer playerId) {
        // return playerDao.deletePlayer(playerId);
        return true;
    }

    private void createDummyPlayers(){
        testPlayers.addAll(Arrays.asList(
                Player.builder().id(1).username("player1").password(HashUtil.hashValue("test")).isActive(Boolean.FALSE).build(),
                Player.builder().id(2).username("player2").password(HashUtil.hashValue("test")).isActive(Boolean.FALSE).build(),
                Player.builder().id(3).username("player3").password(HashUtil.hashValue("test")).isActive(Boolean.FALSE).build(),
                Player.builder().id(4).username("player4").password(HashUtil.hashValue("test")).isActive(Boolean.FALSE).build(),
                Player.builder().id(5).username("player5").password(HashUtil.hashValue("test")).isActive(Boolean.FALSE).build(),
                Player.builder().id(6).username("player6").password(HashUtil.hashValue("test")).isActive(Boolean.FALSE).build()));
    }

}
