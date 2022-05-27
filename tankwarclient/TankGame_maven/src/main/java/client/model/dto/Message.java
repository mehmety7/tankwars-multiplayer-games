package client.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Integer playerId;   //Mesaj gönderirken gerekiyor builderda yoktu, ekledim
    private String playerUserName;
    private String text;

}
