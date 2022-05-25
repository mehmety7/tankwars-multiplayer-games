package server.service;

import com.mysql.cj.util.StringUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.bean.BeanHandler;
import server.dao.InMemoryDao;
import server.model.dto.Message;
import server.model.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter
@RequiredArgsConstructor
public class MessageService {

    public static MessageService messageService;

    public static MessageService getInstance() {
        if (Objects.isNull(messageService)) {
            messageService = new MessageService(BeanHandler.inMemoryDao, BeanHandler.playerService);
        }
        return messageService;
    }

    private final InMemoryDao inMemoryDao;
    private final PlayerService playerService;

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
