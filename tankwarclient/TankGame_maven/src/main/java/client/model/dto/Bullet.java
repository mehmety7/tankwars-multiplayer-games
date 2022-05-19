package client.model.dto;

import lombok.*;
import client.model.enumerated.FaceOrientation;

import java.util.UUID;
/* Tanklar ateş ederken mermilerin çarptığı yere göre serverin aksiyon alması gerek,
 *  Bu yüzden bir mermi sınıfı oluşturdum.*/
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
    private Float movementSpeed;
}
