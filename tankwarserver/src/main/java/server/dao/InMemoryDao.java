package server.dao;

import server.model.dto.*;
import server.utilization.Pair;

import java.util.*;

public class InMemoryDao {

    public static InMemoryDao inMemoryDao;

    public Map<Integer, Game> games;
    public Map<Integer, Tank> tanks;
    public List<Pair<Integer, Bullet>> bullets; //integer burada gameID olmalı. Bulletlarda zaten tankID yani playerID var.
    public List<Statistic> statistics;
    public List<Message> messages;

    public InMemoryDao() {
        games = new HashMap<>();
        tanks = new HashMap<>();
        bullets = new ArrayList<>();
        statistics = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public static InMemoryDao getInstance(){
        if (Objects.isNull(inMemoryDao)) {
            inMemoryDao = new InMemoryDao();
        }
        return inMemoryDao;
    }

}
