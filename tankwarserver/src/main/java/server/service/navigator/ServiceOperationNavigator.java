package server.service.navigator;

import lombok.RequiredArgsConstructor;
import server.bean.BeanHandler;
import server.constants.ConstantsForInnerLogic;
import server.extensions.GameExtension;
import server.model.dto.*;
import server.model.entity.Player;
import server.model.enumerated.MethodType;
import server.model.request.CreateGameRequest;
import server.model.request.PlayerGameRequest;
import server.model.request.UpdateGameRequest;
import server.model.response.UpdateGameResponse;
import server.service.*;
import server.socket.Protocol;
import server.utilization.JsonUtil;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ServiceOperationNavigator {

    private static ServiceOperationNavigator serviceOperationNavigator;

    public static String OK = "OK";
    public static String FAIL = "FL";

    private final PlayerService playerService;
    private final TankService tankService;
    private final GameService gameService;
    private final StatisticService statisticService;
    private final MessageService messageService;

    public static ServiceOperationNavigator getInstance() {
        if (Objects.isNull(serviceOperationNavigator)) {
            serviceOperationNavigator =
                    new ServiceOperationNavigator(BeanHandler.playerService, BeanHandler.tankService,
                            BeanHandler.gameService, BeanHandler.statisticService,
                            BeanHandler.messageService);
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
            PlayerGameRequest request = JsonUtil.fromJson(protocol.getMessage(), PlayerGameRequest.class);
            Game beforeJoin = gameService.getGame(request.getGameId());
            Player player = playerService.getPlayer(request.getPlayerId());
            if(Objects.isNull(beforeJoin) || Boolean.TRUE.equals(beforeJoin.getIsStarted()))
                return FAIL;
            if(Boolean.FALSE.equals(player.getIsActive()))
                return FAIL;
            Game afterJoin = gameService.joinGame(request.getGameId(), request.getPlayerId());
            return OK + JsonUtil.toJson(afterJoin);
        }

        else if(isEqual(protocol, MethodType.RL)) {
            PlayerGameRequest request = JsonUtil.fromJson(protocol.getMessage(), PlayerGameRequest.class);
            Boolean result = gameService.leaveGame(request.getGameId(), request.getPlayerId());
            if(Boolean.FALSE.equals(result)) {
                return FAIL;
            }
            return OK;
        }

        else if (isEqual(protocol, MethodType.LT)){
            PlayerGameRequest request = JsonUtil.fromJson(protocol.getMessage(), PlayerGameRequest.class);
            if (Boolean.FALSE.equals(playerService.logout(request.getPlayerId())))
                return FAIL;
            if (Objects.nonNull(request.getGameId())) {
                tankService.deleteTank(request.getPlayerId());
                gameService.leaveGame(request.getGameId(), request.getPlayerId());
            }
            return OK;
        }

        else if (isEqual(protocol, MethodType.CM)){
            Message message = JsonUtil.fromJson(protocol.getMessage(), Message.class);
            if (Boolean.TRUE.equals(messageService.createMessage(message))) {
                return OK;
            }
            return FAIL;
        }

        else if (isEqual(protocol, MethodType.GM)){
            List<Message> messages = messageService.getMessages();
            if (messages.isEmpty()) {
                return FAIL;
            }
            return OK + JsonUtil.toJson(messages);
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
            Game game = gameService.getGame(JsonUtil.fromJson(protocol.getMessage(), Game.class).getId());
            if(Objects.isNull(game))
                return FAIL;
            if(game.getPlayers().size() < ConstantsForInnerLogic.minimumPlayers)
                return FAIL;
            List<Tank> tanks = gameService.startGame(game.getId());
            return OK + JsonUtil.toJson(tanks);
        }

        else if (isEqual(protocol, MethodType.AS)){
            Game game = JsonUtil.fromJson(protocol.getMessage(), Game.class);
            if(Objects.isNull(game.getId()))
                return FAIL;
            if(game.getPlayers() == null || game.getPlayers().isEmpty())
                return FAIL;
            if(Boolean.FALSE.equals(statisticService.addStatisticsInEndOfGame(game)))
                return FAIL;
            return OK;
        }

        else if (isEqual(protocol, MethodType.GL)){
            List<Statistic> statistics = statisticService.getStatistics();
            if(Objects.isNull(statistics) || statistics.isEmpty())
                return FAIL;
            return OK + JsonUtil.toJson(statistics);
        }

        else if (isEqual(protocol, MethodType.SF)){
            Tank tank = JsonUtil.fromJson(protocol.getMessage(), Tank.class);
            Bullet bullet = tankService.createBullet(tank);
            AtomicReference<Integer> score = tankService.tanksThatGotHit(tank, bullet);
            tankService.removeBullet(bullet);
            gameService.updatePlayerPoint(tank.getGameId(), tank.getPlayerId(), score.get());
            return OK;
        }

        else if (isEqual(protocol, MethodType.UG)){
            UpdateGameRequest gameRequest = JsonUtil.fromJson(protocol.getMessage(), UpdateGameRequest.class);

            Game game = gameService.getGame(gameRequest.getGameId());
            if(Objects.isNull(game) || game.getIsStarted().equals(Boolean.FALSE))
                return FAIL;
            UpdateGameResponse response = UpdateGameResponse.builder()
                    .tanks(game
                            .getPlayers()
                            .keySet()
                            .stream()
                            .map(tankService::getTank)
                            .collect(Collectors.toList()))
                    .bullets(tankService.getBullets(game.getId()))
                    .build();
            return OK + JsonUtil.toJson(response);
        }

        else if (isEqual(protocol, MethodType.UD)){
            Tank tank = JsonUtil.fromJson(protocol.getMessage(), Tank.class);
            Tank record = tankService.getTank(tank.getPlayerId());
            tank.setHealth(record.getHealth());
            tankService.createOrUpdateTank(tank, "UD");
            return OK;
        }


        else if (isEqual(protocol, MethodType.GP)) {
            Player player = playerService.getPlayer(JsonUtil.fromJson(protocol.getMessage(), Player.class).getId());
            if (Objects.isNull(player)) {
                return FAIL;
            }
            return OK + JsonUtil.toJson(player);
        }

        else if(isEqual(protocol, MethodType.AG)) {
            List<Player> players = playerService.getActivePlayers();
            if(players.isEmpty()) {
                return FAIL;
            }
            return OK + JsonUtil.toJson(players);
        }

        else {
            return FAIL;
        }
    }

    private boolean isEqual(Protocol protocol, MethodType methodType) {
        return protocol.getMethodType().equals(methodType.toString());
    }


}
