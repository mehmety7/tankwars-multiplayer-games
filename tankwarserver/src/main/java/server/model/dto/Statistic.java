package server.model.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Statistic extends BaseDto {

    private static final long serialVersionUID = -2133882498133254268L;

    private Integer playerId;
    private String playerUserName;
    private Integer score;

}
