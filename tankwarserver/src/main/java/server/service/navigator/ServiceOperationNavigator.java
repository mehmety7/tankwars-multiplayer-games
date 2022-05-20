package server.service.navigator;

import server.model.entity.Player;
import server.model.enumerated.MethodType;
import server.service.PlayerService;
import server.socket.Protocol;
import server.utilization.JsonUtil;

import java.util.Objects;

public class ServiceOperationNavigator {

    private static ServiceOperationNavigator serviceOperationNavigator;

    public static String OK = "OK";
    public static String FAIL = "FL";

    private PlayerService playerService = PlayerService.getInstance();

    public static ServiceOperationNavigator getInstance() {
        if (Objects.isNull(serviceOperationNavigator)) {
            serviceOperationNavigator = new ServiceOperationNavigator();
        }
        return serviceOperationNavigator;
    }

    public String doOperation(Protocol protocol) {

        if (protocol.getMethodType().equals(MethodType.LG.toString())) {
            Player result = playerService.login(JsonUtil.fromJson(protocol.getMessage(), Player.class));
            if (Objects.nonNull(result)) {
                return OK + JsonUtil.toJson(result);
            } else {
                return FAIL;
            }
        }

        else if (protocol.getMethodType().equals(MethodType.SU.toString())) {
            Player result = playerService.createPlayer(JsonUtil.fromJson(protocol.getMessage(), Player.class));
            if (Objects.nonNull(result)) {
                return OK + JsonUtil.toJson(result);
            } else {
                return FAIL;
            }
        }

        else {
            return OK;
        }

    }

}
