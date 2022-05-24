package server.extensions;

import server.model.dto.Game;
import server.model.request.CreateGameRequest;

public class GameExtension {
    public static Game getGameFromRequest(CreateGameRequest gameRequest){
        return Game.builder().id(gameRequest.getId())
                .tourNumber(gameRequest.getTourNumber())
                .shootingSpeed(gameRequest.getShootingSpeed())
                .mapType(gameRequest.getMapType())
                .build();
    }
}
