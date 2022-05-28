package client.model.dto;

import lombok.*;
import client.model.enumerated.FaceOrientation;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bullet {
    private Integer playerId;
    private UUID bulletId;
    private Integer positionX;
    private Integer positionY;
    private FaceOrientation faceOrientation;
}
