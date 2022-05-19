package client.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import client.model.entity.Player;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private Integer id;
    private Map<Player, Integer> players; // point
    private Integer tourNumber;
    private Float shootingSpeed;
    private String mapType;
    private Boolean isStarted;

}