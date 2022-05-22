package server.service;

import lombok.NoArgsConstructor;
import server.dao.InMemoryDao;
import server.model.dto.Bullet;
import server.utilization.Pair;

import java.util.Objects;

@NoArgsConstructor
public class BulletService {

    private static BulletService bulletService;

    public static BulletService getInstance() {
        if (Objects.isNull(bulletService)) {
            bulletService = new BulletService();
        }

        return bulletService;
    }

    private final InMemoryDao inMemoryDao = InMemoryDao.getInstance();

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
