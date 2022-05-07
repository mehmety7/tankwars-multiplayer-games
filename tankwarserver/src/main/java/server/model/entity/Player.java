package server.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player implements Serializable {

    private static final long serialVersionUID = -3607242305431758911L;

    private Integer id;
    private String username;
    private String password;
    private Boolean isActive;
}
