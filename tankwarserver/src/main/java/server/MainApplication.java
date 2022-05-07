package server;

import server.model.dto.Tank;
import server.service.TankService;

public class MainApplication {

    public static void main(String[] args) {

        TankService tankService = new TankService();
        tankService.createOrUpdateTank(Tank.builder().playerId(1).build());
        System.out.println(tankService.getTank(1));


        /*
        PlayerDao playerDao = PlayerDao.getInstance();

        System.out.println(playerDao.createPlayer(Player.builder().username("adam").password("smith").build()));

        List<Player> players = playerDao.getActivePlayers();
        System.out.println(players.get(0).getUsername());

        Player player = playerDao.getPlayer(2);
        System.out.println(player.getUsername());

        Player player1 = playerDao.getPlayer("adam");
        System.out.println(player1.getPassword());

        playerDao.updateActive(3);

        playerDao.deletePlayer(6);

        playerDao.getDataPersistence().getConnection().close();
        */
    }

}
