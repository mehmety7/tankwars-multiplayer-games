package client.model.dto;

import lombok.*;
import client.model.entity.Player;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private Integer id;
    private Map<Integer, Integer> players; // point
    private Integer tourNumber;
    private Double shootingSpeed;
    private String mapType;
    private Boolean isStarted;

}