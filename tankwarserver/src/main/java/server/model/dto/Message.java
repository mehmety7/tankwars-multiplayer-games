package server.model.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseDto {

    private static final long serialVersionUID = -752945020104104902L;

    private Integer playerId;
    private String playerUserName;
    private String text;

}
