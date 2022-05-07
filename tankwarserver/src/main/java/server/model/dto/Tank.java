package server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tank {

    private Integer playerId;
    private Integer positionX;
    private Integer positionY;
    private Integer health;

}
