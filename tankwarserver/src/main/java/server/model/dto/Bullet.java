package server.model.dto;

import lombok.*;
import server.model.enumerated.FaceOrientation;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bullet extends BaseDto {

    private static final long serialVersionUID = -4551114631940493065L;

    private Integer tankId;
    private UUID bulletId;
    private Integer positionX;
    private Integer positionY;
    private FaceOrientation faceOrientation;
}

