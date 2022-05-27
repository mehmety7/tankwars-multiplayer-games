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

/* Tanklar ateş ederken mermilerin çarptığı yere göre serverin aksiyon alması gerek,
 *  Bu yüzden bir mermi sınıfı oluşturdum.*/
// Mermiler sınırsız hızda düz bir çizgi olacaklarından refactor atıyorum.
// Shoot komutunda client bir uuid oluşturup yollamalı. Veya biz 1 den başlayıp artacak şekilde integer id yapalım
// ve shoot komutunda OK responsunda bu id'yi dönelim
