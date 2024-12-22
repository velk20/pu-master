package org.fmi.streamline.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.fmi.streamline.dtos.message.EditMessageDTO;
import org.fmi.streamline.dtos.message.FriendMessageDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.MessageRepository;
import org.fmi.streamline.repositories.UserRepository;
import org.fmi.streamline.util.ConverterUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final ConverterUtil converterUtil;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public MessageService(ConverterUtil converterUtil, UserRepository userRepository, MessageRepository messageRepository) {
        this.converterUtil = converterUtil;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public MessageEntity save(MessageEntity messageEntity) {
        return this.messageRepository.save(messageEntity);
    }

    public MessageEntity getById(String id) {
        return this.messageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Message with id " + id + " not found"));
    }

    public List<MessageEntity> getAllMessagesForFriend(FriendMessageDTO dto) {
        return this.messageRepository.findMessagesByChannelNullAndReceiverAuthorSwapped(dto.getFriendId(), dto.getUserId());
    }

    @Transactional
    public void deleteFriendsMessages(String requesterUsername, String friendUsername) {
        this.messageRepository.deleteMessagesBetweenFriends(requesterUsername,friendUsername);
    }

    public void deleteMessage(String messageId, String userId) {
        findUserById(userId);

        MessageEntity message = this.getById(messageId);
        if (!message.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to delete this message (Not owner)");
        }

        message.setDeleted(true);
        this.messageRepository.save(message);
    }

    public MessageDTO editMessage(String messageId, EditMessageDTO dto) {
        this.findUserById(dto.getUserId());
        MessageEntity message = this.getById(messageId);
        message.setContent(dto.getContent());

        return converterUtil.convertToDTO(this.messageRepository.save(message));
    }

    private UserEntity findUserById(String userId) {
        return this.userRepository.findByIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }
}
