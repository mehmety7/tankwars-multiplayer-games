package server.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.bean.BeanHandler;
import server.dao.InMemoryDao;
import server.model.dto.Game;
import server.model.dto.Statistic;
import server.model.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
@RequiredArgsConstructor
public class StatisticService {

    public static StatisticService statisticService;

    public static StatisticService getInstance() {
        if (Objects.isNull(statisticService)) {
            statisticService = new StatisticService(BeanHandler.inMemoryDao, BeanHandler.playerService, BeanHandler.gameService);
        }
        return statisticService;
    }

    private final InMemoryDao inMemoryDao;
    private final PlayerService playerService;
    private final GameService gameService;

    public Boolean addStatisticsInEndOfGame(Game game) {
        List<Player> players = new ArrayList<>();

        for (Integer playerId : game.getPlayers().keySet()) {
            players.add(playerService.getPlayer(playerId));
        }

        for (Player player : players) {
            addStatistic(player.getId(), player.getUsername(), game.getPlayers().get(player));
        }

        gameService.deleteGame(game.getId());

        return Boolean.TRUE;
    }

    private void addStatistic(Integer playerId, String username, Integer score) {
        Boolean isExist = Boolean.FALSE;
        for (Statistic statistic : inMemoryDao.statistics) {
            if (statistic.getPlayerId().equals(playerId)) {
                statistic.setScore(statistic.getScore() + score);
                isExist = Boolean.TRUE;
            }
        }

        if (isExist.equals(Boolean.FALSE)) {
            Statistic statistic = Statistic.builder().playerId(playerId).playerUserName(username).score(score).build();
            inMemoryDao.statistics.add(statistic);
        }
    }

    public Statistic getStatistic(Integer playerId) {
        for (Statistic statistic : inMemoryDao.statistics) {
            if (playerId.equals(statistic.getPlayerId())) {
                return statistic;
            }
        }
        return null;
    }

    public List<Statistic> getStatistics() {
        return inMemoryDao.statistics.stream().sorted(Comparator.comparing(Statistic::getScore).reversed()).collect(Collectors.toList());
    }



}
