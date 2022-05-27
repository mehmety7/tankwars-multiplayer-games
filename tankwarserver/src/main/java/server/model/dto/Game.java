package server.model.dto;

import lombok.*;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game extends BaseDto {

    private static final long serialVersionUID = 1833262976822214071L;

    private Integer id;
    private Map<Integer, Integer> players; // playerId - point
    private Integer tourNumber;
    private Double shootingSpeed;
    private String mapType;
    private Boolean isStarted;

}
