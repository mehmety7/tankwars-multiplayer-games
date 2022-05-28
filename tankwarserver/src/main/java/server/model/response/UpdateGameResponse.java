package server.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.model.dto.Bullet;
import server.model.entity.Player;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateGameResponse {
    private List<Player> players;
    private List<Bullet> bullets;
}
