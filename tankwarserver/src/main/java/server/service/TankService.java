package server.service;

import server.dao.InMemoryDao;
import server.model.dto.Tank;

public class TankService {

    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();

    public Tank createOrUpdateTank(Tank tank) {
        inMemoryDao.tanks.put(tank.getPlayerId(), tank);
        return tank;
    }

    public Tank getTank(Integer playerId) {
        return inMemoryDao.tanks.get(playerId);
    }

    public Tank deleteTank(Integer playerId) {
        return inMemoryDao.tanks.remove(playerId);
    }

}
