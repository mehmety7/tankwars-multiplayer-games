package server.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.bean.BeanHandler;
import server.constants.ConstantsForInnerLogic;
import server.dao.InMemoryDao;
import server.model.dto.Bullet;
import server.model.dto.Game;
import server.model.dto.Tank;
import server.model.entity.Player;
import server.model.enumerated.FaceOrientation;
import server.utilization.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Setter
public class TankService {

    public static TankService tankService;

    public static TankService getInstance() {
        if (Objects.isNull(tankService)) {
            tankService = new TankService(BeanHandler.inMemoryDao, BeanHandler.playerService);
        }
        return tankService;
    }

    private final InMemoryDao inMemoryDao;
    private final PlayerService playerService;

    public List<Tank> createTanksForNewGame(Integer gameId) {
        Game game = inMemoryDao.games.get(gameId);
        Integer iterator = 1;

        List<Player> players = new ArrayList<>();

        for (Integer playerId : game.getPlayers().keySet()) {
            players.add(playerService.getPlayer(playerId));
        }

        for (Player player : players) {
            FaceOrientation faceOrientation;
            Integer newX;
            Integer newY;
            if (iterator.equals(1)) {
                faceOrientation = FaceOrientation.UP;
                newX = 0;
                newY = 0;
            } else if (iterator.equals(2)) {
                faceOrientation = FaceOrientation.DOWN;
                newX = 0;
                newY = 100;
            } else if (iterator.equals(3)) {
                faceOrientation = FaceOrientation.DOWN;
                newX = 100;
                newY = 100;
            } else {
                faceOrientation = FaceOrientation.UP;
                newX = 100;
                newY = 0;
            }
            Integer health = 100;
            Tank tank = Tank.builder()
                    .playerId(player.getId())
                    .gameId(gameId)
                    .faceOrientation(faceOrientation)
                    .health(health)
                    .positionX(newX)
                    .positionY(newY)
                    .build();
            createOrUpdateTank(tank);
        }
        return getTanksInGame(gameId);
    }

    public void createOrUpdateTank(Tank tank) {
        inMemoryDao.tanks.put(tank.getPlayerId(), tank);
    }

    public Tank getTank(Integer playerId) {
        return inMemoryDao.tanks.get(playerId);
    }

    public List<Tank> getTanksInGame(Integer gameId) {
        List<Tank> tanks = new ArrayList<>();
        for (Tank tank : inMemoryDao.tanks.values()) {
            if (tank.getGameId().equals(gameId)) {
                tanks.add(tank);
            }
        }
        return tanks;
    }

    public Tank deleteTank(Integer playerId) {
        removeBulletsForPlayer(playerId);
        return inMemoryDao.tanks.remove(playerId);
    }

    public Bullet createBullet(Tank tank){
        int offsetX = 0;
        int offsetY = 0;
        switch (tank.getFaceOrientation()){
            case LEFT:
                offsetY = 0;
                offsetX = -1 * ConstantsForInnerLogic.bulletOffset;
                break;
            case RIGHT:
                offsetY = 0;
                offsetX = ConstantsForInnerLogic.bulletOffset;
                break;
            case UP:
                offsetX = 0;
                offsetY = -1 * ConstantsForInnerLogic.bulletOffset;
                break;
            case DOWN:
                offsetX = 0;
                offsetY = ConstantsForInnerLogic.bulletOffset;
                break;
        }
        Bullet bullet = Bullet.builder()
                .tankId(tank.getPlayerId())
                .bulletId(UUID.randomUUID())
                .positionX(inMemoryDao.tanks.get(tank.getPlayerId()).getPositionX() + offsetX)
                .positionY(inMemoryDao.tanks.get(tank.getPlayerId()).getPositionY() + offsetY)
                .faceOrientation(inMemoryDao.tanks.get(tank.getPlayerId()).getFaceOrientation())
                .build();
        createOrUpdateBullet(bullet);
        return bullet;
    }

    public Bullet createOrUpdateBullet(Bullet bullet){
        inMemoryDao.bullets.removeIf(bulletPair -> bulletPair.getSecond().getTankId().equals(bullet.getTankId()) && bulletPair.getSecond().getBulletId().equals(bullet.getBulletId()));
        inMemoryDao.bullets.add(new Pair<>(bullet.getTankId(), bullet));
        return bullet;
    }

    public void removeBulletsForPlayer(Integer playerId){
        inMemoryDao.bullets.removeIf(bulletPair -> bulletPair.getSecond().getTankId().equals(playerId));
    }

    public void removeBulletsForGame(Integer gameId){
        inMemoryDao.bullets.removeIf(integerBulletPair -> integerBulletPair.getFirst().equals(gameId));
    }

    private boolean isTankGotHit(Bullet bullet, Tank tank){
        switch (bullet.getFaceOrientation()){
            case UP:
                if ((tank.getPositionX() + ConstantsForInnerLogic.tankSize / 2 >= bullet.getPositionX() && tank.getPositionX() - ConstantsForInnerLogic.tankSize / 2 <= bullet.getPositionX() + 1) && tank.getPositionY() <= bullet.getPositionY()) {
                    return true;
                }
                break;
            case DOWN:
                if ((tank.getPositionX() + ConstantsForInnerLogic.tankSize / 2 >= bullet.getPositionX() && tank.getPositionX() - ConstantsForInnerLogic.tankSize / 2 <= bullet.getPositionX() + 1) && tank.getPositionY() >= bullet.getPositionY()) {
                    return true;
                }
                break;
            case LEFT:
                if ((tank.getPositionY() + ConstantsForInnerLogic.tankSize / 2 >= bullet.getPositionY() && tank.getPositionY() - ConstantsForInnerLogic.tankSize / 2 <= bullet.getPositionY() + 1) && tank.getPositionX() <= bullet.getPositionX()) {
                    return true;
                }
                break;
            case RIGHT:
                if ((tank.getPositionY() + ConstantsForInnerLogic.tankSize / 2 >= bullet.getPositionY() && tank.getPositionY() - ConstantsForInnerLogic.tankSize / 2 <= bullet.getPositionY() + 1) && tank.getPositionX() >= bullet.getPositionX()) {
                    return true;
                }
                break;
        }
        return false;
    }

    public Integer tanksThatGotHit(Tank tank, Bullet bullet){
        Integer score = 0;
        List<Tank> hittedTankList = new ArrayList<>();
        List<Tank> allTanks = tankService.getTanksInGame(tank.getGameId());
        allTanks.forEach((tankIn -> {
            if(isTankGotHit(bullet, tankIn)){
                int newHealth = tankIn.getHealth() - ConstantsForInnerLogic.bulletDamage;
                tankIn.setHealth(newHealth);
                if(tankIn.getHealth() < 0)
                    tankIn.setHealth(0);
                hittedTankList.add(tankIn);
            }
        }));

        hittedTankList.forEach(tankIn -> {
            if(tankIn.getHealth() == 0){
                deleteTank(tankIn.getPlayerId());
            } else {
                createOrUpdateTank(tankIn);
            }
        });

        if (Boolean.FALSE.equals(hittedTankList.isEmpty())) {
            score = 1;
        }

        return score;
    }

    public List<Bullet> getBullets(Integer gameId){
        return inMemoryDao.bullets
                .stream()
                .filter(integerBulletPair -> integerBulletPair.getFirst().equals(gameId))
                .map(Pair::getSecond)
                .collect(Collectors.toList());
    }

    public void removeBullet(Bullet bullet){
        inMemoryDao.bullets.remove(new Pair<>(bullet.getTankId(), bullet));
    }

}
