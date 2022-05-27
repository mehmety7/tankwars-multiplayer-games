package server.model.entity;

import lombok.*;
import server.model.dto.BaseDto;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player extends BaseDto {

    private static final long serialVersionUID = -3607242305431758911L;

    private Integer id;
    private String username;
    private String password;
    private Boolean isActive;
}
