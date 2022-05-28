package client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGameRequest {
    private Integer id;
    private Integer tourNumber;
    private Double shootingSpeed;
    private String mapType;
}
