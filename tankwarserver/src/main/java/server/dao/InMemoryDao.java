package server.dao;

import server.model.dto.Game;
import server.model.dto.Message;
import server.model.dto.Statistic;
import server.model.dto.Tank;

import java.util.*;

public class InMemoryDao {

    public static InMemoryDao inMemoryDao;

    public Map<Integer, Game> games;
    public Map<Integer, Tank> tanks;
    public List<Statistic> statistics;
    public List<Message> messages;

    public InMemoryDao() {
        games = new HashMap<>();
        tanks = new HashMap<>();
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
