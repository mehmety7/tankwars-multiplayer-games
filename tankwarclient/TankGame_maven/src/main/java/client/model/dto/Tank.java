package client.model.dto;

import client.model.enumerated.FaceOrientation;
import lombok.*;

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
