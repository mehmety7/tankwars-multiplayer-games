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
            return OK;
        }

        else if (isEqual(protocol, MethodType.JG)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.LT)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.CG)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.GG)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.SG)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.AS)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.GL)){
            return OK;
        }

        else if (isEqual(protocol, MethodType.SF)){
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
