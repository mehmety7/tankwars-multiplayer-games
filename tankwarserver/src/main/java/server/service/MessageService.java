package server.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.dao.InMemoryDao;
import server.model.dto.Message;

import java.util.List;
import java.util.Objects;

@Setter
@RequiredArgsConstructor
public class MessageService {

    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();
    private PlayerService playerService = PlayerService.getInstance();

    public Boolean createMessage(Integer playerId, Message message) {
        if (Objects.nonNull(playerId)){
            String username = playerService.getPlayer(playerId).getUsername();
            message.setPlayerUserName(username);
            inMemoryDao.messages.add(message);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public List<Message> getMessages() {
        return inMemoryDao.messages;
    }

}
