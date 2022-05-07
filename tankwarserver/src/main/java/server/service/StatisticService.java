package server.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.dao.InMemoryDao;
import server.model.dto.Statistic;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@RequiredArgsConstructor
public class StatisticService {

    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();
    private PlayerService playerService = PlayerService.getInstance();

    public Boolean addStatistic(Statistic statistic) {
        for (Statistic iter : inMemoryDao.statistics) {
            if (iter.getPlayerId().equals(statistic.getPlayerId())) {
                Integer newPoint = statistic.getScore() + iter.getScore();
                statistic.setScore(newPoint);
                inMemoryDao.statistics.remove(iter);
            }
        }
        inMemoryDao.statistics.add(statistic);
        return Boolean.TRUE;
    }

    public Statistic getStatistic(Integer playerId) {
        for (Statistic statistic : inMemoryDao.statistics) {
            if (playerId.equals(statistic.getPlayerId())) {
                return statistic;
            }
        }
        return Statistic.builder().build();
    }

    public List<Statistic> getStatistics() {
        return inMemoryDao.statistics.stream().sorted(Comparator.comparing(Statistic::getScore).reversed()).collect(Collectors.toList());
    }



}
