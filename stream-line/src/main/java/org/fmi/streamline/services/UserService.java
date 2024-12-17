package org.fmi.streamline.services;

import jakarta.validation.Valid;
import org.fmi.streamline.dtos.channel.AddOrRemoveUserToChannelDTO;
import org.fmi.streamline.dtos.message.FriendMessageDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.message.SendMessageToFriendDTO;
import org.fmi.streamline.dtos.user.AddFriendDTO;
import org.fmi.streamline.dtos.user.UserDetailDTO;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.UserRepository;
import org.fmi.streamline.util.ConverterUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ConverterUtil converterUtil;
    private final UserRepository userRepository;
    private final MessageService messageService;

    public UserService(ModelMapper modelMapper, ConverterUtil converterUtil, UserRepository userRepository, MessageService messageService) {
        this.converterUtil = converterUtil;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(()-> new EntityNotFoundException("User with username " + username + " not found"));
    }

    public UserEntity getById(String id) {
        return this.userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(()-> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public UserDetailDTO addFriend(AddFriendDTO dto) {
        if (dto.getFriendUsername().equals(dto.getRequesterUsername())) {
            throw new IllegalArgumentException("User can't add themselves as friend");
        }

        UserEntity requesterFriend = this.getByUsername(dto.getRequesterUsername());
        UserEntity newFriend = this.getByUsername(dto.getFriendUsername());

        if (requesterFriend.getFriends().contains(newFriend)
                || newFriend.getFriends().contains(requesterFriend)) {
            throw new IllegalArgumentException("User already is friend");
        }

        requesterFriend.addFriend(newFriend);
        newFriend.addFriend(requesterFriend);

        this.userRepository.save(requesterFriend);
        this.userRepository.save(newFriend);

        return converterUtil.convertToDTO(requesterFriend);
    }

    public List<UserDetailDTO> getAllAvailableFriends(String userId) {
        return this.userRepository.findAvailableFriends(userId).stream().map(converterUtil::convertToDTO).toList();
    }

    public UserDetailDTO removeFriend( AddFriendDTO dto) {
        if (dto.getFriendUsername().equals(dto.getRequesterUsername())) {
            throw new IllegalArgumentException("User can't remove themselves as friend");
        }

        UserEntity requesterFriend = this.getByUsername(dto.getRequesterUsername());
        UserEntity newFriend = this.getByUsername(dto.getFriendUsername());

        if (!requesterFriend.getFriends().contains(newFriend)
                || !newFriend.getFriends().contains(requesterFriend)) {
            throw new IllegalArgumentException("User is not your friend friend");
        }

        requesterFriend.removeFriend(newFriend);
        newFriend.removeFriend(requesterFriend);

        UserEntity updatedUser = this.userRepository.save(requesterFriend);
        this.userRepository.save(newFriend);

        return converterUtil.convertToDTO(updatedUser);
    }

    public List<MessageDTO> getFriendMessages(FriendMessageDTO dto) {
        return this.messageService.getAllMessagesForFriend(dto).stream().map(converterUtil::convertToDTO).collect(Collectors.toList());
    }

    public List<UserEntity> findUsersNotInChannel(String channelId) {
        return this.userRepository.findUsersNotInChannel(channelId);
    }

    public Optional<UserEntity> isUserInChannel(AddOrRemoveUserToChannelDTO dto) {
        return this.userRepository.findUserInChannel(dto.getUsername(), dto.getChannelId());
    }

    public MessageDTO sendMessageToFriend(SendMessageToFriendDTO dto) {
        UserEntity receiverUser = this.getById(dto.getFriendId());
        UserEntity senderUser = this.getById(dto.getSenderId());

        MessageEntity newMessage = MessageEntity.builder()
                .receiver(receiverUser)
                .channel(null)
                .content(dto.getMessage())
                .timestamp(LocalDateTime.now())
                .deleted(false)
                .author(senderUser)
                .build();

        MessageEntity saved = this.messageService.save(newMessage);
        return converterUtil.convertToDTO(saved);
    }
}
