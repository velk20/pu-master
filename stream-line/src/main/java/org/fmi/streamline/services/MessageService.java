package org.fmi.streamline.services;

import org.fmi.streamline.dtos.message.FriendMessageDTO;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageEntity save(MessageEntity messageEntity) {
        return this.messageRepository.save(messageEntity);
    }

    public List<MessageEntity> getAllMessagesForFriend(FriendMessageDTO dto) {
        return this.messageRepository.findAllByChannelIsNullAndReceiverIdAndAuthorId(dto.getFriendId(), dto.getUserId());
    }
}
