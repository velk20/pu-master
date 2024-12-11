package org.fmi.streamline.services;

import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageEntity save(MessageEntity messageEntity) {
        return this.messageRepository.save(messageEntity);
    }
}
