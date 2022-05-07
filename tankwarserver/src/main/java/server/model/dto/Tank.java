package server.model.dto;

import lombok.*;
import server.model.enumerated.FaceOrientation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tank {

    private Integer playerId;
    private Integer gameId;
    private Integer positionX;
    private Integer positionY;
    private FaceOrientation faceOrientation;
    private Integer health;

}
