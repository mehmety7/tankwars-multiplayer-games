package server.service;

import server.dao.InMemoryDao;
import server.model.dto.Bullet;
import server.utilization.Pair;

public class BulletService {

    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();

    public Bullet createOrUpdateBullet(Bullet bullet){
        inMemoryDao.bullets.forEach((element) -> {
            if(element.getSecond().getPlayerId().equals(bullet.getPlayerId()) && element.getSecond().getBulletId().equals(bullet.getBulletId())) {
                inMemoryDao.bullets.remove(element);
            }
        });
        inMemoryDao.bullets.add(new Pair<>(bullet.getPlayerId(), bullet));
        return bullet;
    }

    public void removeBullets(Integer playerId){
        inMemoryDao.bullets.forEach((element) -> {
            if(element.getFirst().equals(playerId))
                inMemoryDao.bullets.remove(element);
        });
    }
}
