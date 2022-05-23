package server.service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import server.dao.InMemoryDao;
import server.model.dto.Game;
import server.model.dto.Tank;

import java.util.*;

@Setter
@NoArgsConstructor
public class GameService {

    private static final Integer INITIAL_SCORE_POINT = 0;

    public static GameService gameService;

    public static GameService getInstance() {
        if (Objects.isNull(gameService)) {
            gameService = new GameService();
        }
        return gameService;
    }

    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();
    private TankService tankService = TankService.getInstance();

    public Game createGame(Game game) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(game.getId(), INITIAL_SCORE_POINT);

        game.setPlayers(map);
        game.setIsStarted(Boolean.FALSE);

        inMemoryDao.games.put(game.getId(), game);

        return game;
    }

    public Game joinGame(Integer gameId, Integer playerId) {
        inMemoryDao.games.get(gameId).getPlayers().put(playerId, INITIAL_SCORE_POINT);
        return inMemoryDao.games.get(gameId);
    }

    public List<Tank> startGame(Integer gameId) {
        Game game = inMemoryDao.games.get(gameId);
        game.setIsStarted(Boolean.TRUE);

        if (game.getPlayers().size() < 2) {
            return Collections.emptyList();
        }

        return tankService.createTanksForNewGame(gameId);
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
        List<Tank> tanksInGame = tankService.getTanksInGame(gameId);
        for (Tank tank : tanksInGame) {
            inMemoryDao.tanks.remove(tank.getPlayerId());
        }
        return Boolean.TRUE;
    }

    public Boolean updatePlayerPoint(Integer gameId, Integer playerId, Integer point) {
        Integer oldScore = inMemoryDao.games.get(gameId).getPlayers().get(playerId);
        inMemoryDao.games.get(gameId).getPlayers().put(playerId, oldScore + point);
        return Boolean.TRUE;
    }

}
