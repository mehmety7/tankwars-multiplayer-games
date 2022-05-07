package server.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.dao.InMemoryDao;
import server.model.dto.Game;
import server.model.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@RequiredArgsConstructor
public class GameService {

    private static final Integer INITIAL_SCORE_POINT = 0;

    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();
    private PlayerService playerService = PlayerService.getInstance();

    public Game createGame(Integer playerId, Game game) {
        Player player = playerService.getPlayer(playerId);

        Map<Player, Integer> map = new HashMap<>();
        map.put(player, INITIAL_SCORE_POINT);

        game.setPlayers(map);
        game.setId(playerId);
        game.setIsStarted(Boolean.FALSE);

        inMemoryDao.games.put(playerId, game);

        return game;
    }

    public Game joinGame(Integer gameId, Integer playerId) {
        Player player = playerService.getPlayer(playerId);
        inMemoryDao.games.get(gameId).getPlayers().put(player, INITIAL_SCORE_POINT);
        return inMemoryDao.games.get(gameId);
    }

    public Boolean startGame(Integer gameId) {
        inMemoryDao.games.get(gameId).setIsStarted(Boolean.TRUE);
        return Boolean.TRUE;
    }

    public Game getGame(Integer gameId) {
        return inMemoryDao.games.get(gameId);
    }

    public List<Game> getAllGames() {
        return new ArrayList<>(inMemoryDao.games.values());
    }

    public List<Game> getLobbies() {
        Map<Integer, Game> allGames = inMemoryDao.games;
        Map<Integer, Game> lobbies = new HashMap<>();
        for (Game game : allGames.values()) {
            if (Boolean.FALSE.equals(game.getIsStarted())) {
                lobbies.put(game.getId(), game);
            }
        }
        return new ArrayList<>(lobbies.values());
    }

    public Boolean deleteGame(Integer gameId) {
        inMemoryDao.games.remove(gameId);
        return Boolean.TRUE;
    }

    public Boolean updatePlayerPoint(Integer gameId, Integer playerId, Integer point) {
        Player player = playerService.getPlayer(playerId);
        inMemoryDao.games.get(gameId).getPlayers().put(player, point);
        return Boolean.TRUE;
    }

}
