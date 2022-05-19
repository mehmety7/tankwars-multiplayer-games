package client.model.dto;

import lombok.*;
import client.model.enumerated.FaceOrientation;

import java.awt.image.BufferedImage;

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
