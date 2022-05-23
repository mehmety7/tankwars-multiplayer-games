package server.service;

import com.mysql.cj.util.StringUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.dao.InMemoryDao;
import server.model.dto.Message;
import server.model.entity.Player;

import java.util.*;

@Setter
@NoArgsConstructor
public class MessageService {

    public static MessageService messageService;

    public static MessageService getInstance() {
        if (Objects.isNull(messageService)) {
            messageService = new MessageService();
        }
        return messageService;
    }

    private InMemoryDao inMemoryDao = InMemoryDao.getInstance();
    private PlayerService playerService = PlayerService.getInstance();

    public Boolean createMessage(Message message) {
        if (StringUtils.isEmptyOrWhitespaceOnly(message.getText())) {
            return Boolean.FALSE;
        }
        if (Objects.nonNull(message.getPlayerUserName())){
            message.setPlayerUserName(message.getPlayerUserName());
            inMemoryDao.messages.add(message);
            return Boolean.TRUE;
        }
        Player player = playerService.getPlayer(message.getPlayerId());
        if (Objects.nonNull(player)){
            message.setPlayerUserName(player.getUsername());
            inMemoryDao.messages.add(message);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public List<Message> getMessages() {
        if (inMemoryDao.messages.isEmpty()) {
            return Collections.emptyList();
        }
        return inMemoryDao.messages;
    }

}
