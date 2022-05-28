package server.bean;

import server.dao.DataPersistence;
import server.dao.InMemoryDao;
import server.dao.PlayerDao;
import server.service.*;
import server.service.navigator.ServiceOperationNavigator;

public class BeanHandler {

    public static final DataPersistence dataPersistence = null;
    public static final InMemoryDao inMemoryDao = InMemoryDao.getInstance();
    public static final PlayerDao playerDao = null;

    public static final PlayerService playerService = PlayerService.getInstance();
    public static final TankService tankService = TankService.getInstance();
    public static final GameService gameService = GameService.getInstance();
    public static final MessageService messageService = MessageService.getInstance();
    public static final StatisticService statisticService = StatisticService.getInstance();

    public static final ServiceOperationNavigator serviceOperationNavigator = ServiceOperationNavigator.getInstance();

}
