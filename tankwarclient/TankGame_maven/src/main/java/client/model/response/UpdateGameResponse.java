package client.model.response;

import client.model.dto.Bullet;
import client.model.dto.Tank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateGameResponse {
    private List<Tank> tanks;
    private List<Bullet> bullets;
}
