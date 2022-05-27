package server.model.dto;

import lombok.*;
import server.model.enumerated.FaceOrientation;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tank extends BaseDto {

    private static final long serialVersionUID = -1189105722430448141L;

    private Integer playerId;
    private Integer gameId;
    private Integer positionX;
    private Integer positionY;
    private FaceOrientation faceOrientation;
    private Integer health;

}
