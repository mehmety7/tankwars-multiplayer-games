package server.service.navigator;

import server.constants.ConstantsForInnerLogic;
import server.extensions.GameExtension;
import server.model.dto.Game;
import server.model.dto.Statistic;
import server.model.dto.Tank;
import server.model.entity.Player;
import server.model.enumerated.MethodType;
import server.model.request.CreateGameRequest;
import server.model.request.JoinGameRequest;
import server.service.GameService;
import server.service.PlayerService;
import server.service.StatisticService;
import server.service.TankService;
import server.socket.Protocol;
import server.utilization.JsonUtil;

import java.util.List;
import java.util.Objects;

public class ServiceOperationNavigator {

    private static ServiceOperationNavigator serviceOperationNavigator;

    public static String OK = "OK";
    public static String FAIL = "FL";

    private final PlayerService playerService = PlayerService.getInstance();
    private final GameService gameService = GameService.getInstance();
    private final StatisticService statisticService = StatisticService.getInstance();
    private final TankService tankService = TankService.getInstance();

    public static ServiceOperationNavigator getInstance() {
        if (Objects.isNull(serviceOperationNavigator)) {
            serviceOperationNavigator = new ServiceOperationNavigator();
        }
        return serviceOperationNavigator;
    }

    public String doOperation(Protocol protocol) {

        if (isEqual(protocol, MethodType.LG)) {
            Player result = playerService.login(JsonUtil.fromJson(protocol.getMessage(), Player.class));
            if (Objects.nonNull(result)) {
                return OK + JsonUtil.toJson(result);
            } else {
                return FAIL;
            }
        }

        else if (isEqual(protocol, MethodType.SU)) {
            Player result = playerService.createPlayer(JsonUtil.fromJson(protocol.getMessage(), Player.class));
            if (Objects.nonNull(result)) {
                return OK + JsonUtil.toJson(result);
            } else {
                return FAIL;
            }
        }

        else if (isEqual(protocol, MethodType.GU)){
            List<Game> unStartedGames = gameService.getAllGames();
            unStartedGames.removeIf((Game::getIsStarted));
            if(unStartedGames.isEmpty())
                return FAIL;
            return OK + JsonUtil.toJson(unStartedGames);
        }

        else if (isEqual(protocol, MethodType.JG)){
            JoinGameRequest request = JsonUtil.fromJson(protocol.getMessage(), JoinGameRequest.class);
            Game beforeJoin = gameService.getGame(request.getGameId());
            Player player = playerService.getPlayer(request.getPlayerId());
            if(Boolean.TRUE.equals(beforeJoin.getIsStarted()))
                return FAIL;
            if(Boolean.FALSE.equals(player.getIsActive()))
                return FAIL;
            Game afterJoin = gameService.joinGame(request.getGameId(), request.getPlayerId());
            return OK + JsonUtil.toJson(afterJoin);
        }

        else if (isEqual(protocol, MethodType.LT)){
            Player playerBeforeLogOut = JsonUtil.fromJson(protocol.getMessage(), Player.class);
            if(Objects.nonNull(playerBeforeLogOut.getIsActive()) && Boolean.FALSE.equals(playerBeforeLogOut.getIsActive()))
                return FAIL;
            if(Boolean.FALSE.equals(playerService.logout(playerBeforeLogOut)))
                return FAIL;
            Player playerAfterLogOut = playerService.getPlayer(playerBeforeLogOut.getId());
            return OK + JsonUtil.toJson(playerAfterLogOut);
        }

        else if (isEqual(protocol, MethodType.CG)){
            CreateGameRequest gameRequest = JsonUtil.fromJson(protocol.getMessage(), CreateGameRequest.class);
            if(Objects.isNull(playerService.getPlayer(gameRequest.getId()))){
                return FAIL;
            }
            Game createdGame = gameService.createGame(GameExtension.getGameFromRequest(gameRequest));
            return OK + JsonUtil.toJson(createdGame);
        }

        else if (isEqual(protocol, MethodType.GG)){
            Game game = JsonUtil.fromJson(protocol.getMessage(), Game.class);
            Game foundedGame = gameService.getGame(game.getId());
            if(Objects.isNull(foundedGame))
                return FAIL;
            return OK + JsonUtil.toJson(foundedGame);
        }

        else if (isEqual(protocol, MethodType.SG)){
            Game game = JsonUtil.fromJson(protocol.getMessage(), Game.class);
            if(gameService.getGame(game.getId()) == null)
                return FAIL;
            if(game.getPlayers().size() < ConstantsForInnerLogic.minimumPlayers)
                return FAIL;
            List<Tank> tanks = gameService.startGame(game.getId());
            return OK + JsonUtil.toJson(tanks);
        }

        else if (isEqual(protocol, MethodType.AS)){
            Game game = JsonUtil.fromJson(protocol.getMessage(), Game.class);
            if(game.getId() == null)
                return FAIL;
            if(game.getPlayers() == null || game.getPlayers().size() == 0)
                return FAIL;
            if(!statisticService.addStatisticsInEndOfGame(game))
                return FAIL;
            return OK;
        }

        else if (isEqual(protocol, MethodType.GL)){
            List<Statistic> statistics = statisticService.getStatistics();
            if(statistics == null || statistics.size() == 0)
                return FAIL;
            return OK + JsonUtil.toJson(statistics);
        }

        else if (isEqual(protocol, MethodType.SF)){
            Tank tank = JsonUtil.fromJson(protocol.getMessage(), Tank.class);
            tankService.createBullet(tank);
            return OK;
        }

        else if (isEqual(protocol, MethodType.UG)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.UD)){
            return OK;
        }

        else {
            return FAIL;
        }
    }

    private boolean isEqual(Protocol protocol, MethodType methodType) {
        return protocol.getMethodType().equals(methodType.toString());
    }


}
