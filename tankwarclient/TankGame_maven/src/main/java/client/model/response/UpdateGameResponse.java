package client.model.response;

import client.model.dto.Bullet;
import client.model.entity.Player;
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
    private List<Player> players;
    private List<Bullet> bullets;
}
