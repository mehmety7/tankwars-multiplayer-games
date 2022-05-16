package server.service;

import server.dao.InMemoryDao;
import server.model.dto.Bullet;
import server.utilization.Pair;

public class BulletService {
    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();
    public Bullet createOrUpdateBullet(Bullet bullet){
        inMemoryDao.bullets.forEach((n) -> {
            if(n.getSecond().getPlayerId().equals(bullet.getPlayerId()) && n.getSecond().getBulletId().equals(bullet.getBulletId())) {
                inMemoryDao.bullets.remove(n);
            }
        });
        inMemoryDao.bullets.add(new Pair<>(bullet.getPlayerId(), bullet));
        return bullet;
    }
    public void removeBullets(Integer playerId){
        inMemoryDao.bullets.forEach((n) -> {
            if(n.getFirst().equals(playerId))
                inMemoryDao.bullets.remove(n);
        });
    }
}
