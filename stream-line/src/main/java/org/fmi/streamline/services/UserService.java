package org.fmi.streamline.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.fmi.streamline.auth.service.CustomUserDetails;
import org.fmi.streamline.dtos.channel.AddOrRemoveUserToChannelDTO;
import org.fmi.streamline.dtos.message.FriendMessageDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.message.SendMessageToFriendDTO;
import org.fmi.streamline.dtos.user.AddOrRemoveFriendDTO;
import org.fmi.streamline.dtos.user.ChangeUserPasswordDTO;
import org.fmi.streamline.dtos.user.UpdateProfileDTO;
import org.fmi.streamline.dtos.user.UserDetailDTO;
import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.UserRepository;
import org.fmi.streamline.util.ConverterUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ConverterUtil converterUtil;
    private final UserRepository userRepository;
    private final ChannelMembershipService channelMembershipService;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;


    public UserService(ModelMapper modelMapper, ConverterUtil converterUtil, UserRepository userRepository, ChannelMembershipService channelMembershipService, MessageService messageService, PasswordEncoder passwordEncoder) {
        this.converterUtil = converterUtil;
        this.userRepository = userRepository;
        this.channelMembershipService = channelMembershipService;
        this.messageService = messageService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(()-> new EntityNotFoundException("User with username " + username + " not found"));
    }

    public UserEntity getById(String id) {
        return this.userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(()-> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public UserDetailDTO addFriend(AddOrRemoveFriendDTO dto) {
        if (dto.getFriendUsername().equals(dto.getRequesterUsername())) {
            throw new IllegalArgumentException("User can't add themselves as friend");
        }

        UserEntity requesterFriend = this.getByUsername(dto.getRequesterUsername());
        UserEntity newFriend = this.getByUsername(dto.getFriendUsername());

        if (requesterFriend.getFriends().contains(newFriend)
                || newFriend.getFriends().contains(requesterFriend)) {
            throw new IllegalArgumentException(newFriend.getUsername() + " is already your friend");
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

    public UserDetailDTO removeFriend( AddOrRemoveFriendDTO dto) {
        if (dto.getFriendUsername().equals(dto.getRequesterUsername())) {
            throw new IllegalArgumentException("User can't remove themselves as friend");
        }

        UserEntity requesterFriend = this.getByUsername(dto.getRequesterUsername());
        UserEntity newFriend = this.getByUsername(dto.getFriendUsername());

        if (!requesterFriend.getFriends().contains(newFriend)
                || !newFriend.getFriends().contains(requesterFriend)) {
            throw new IllegalArgumentException("User is not your friend friend");
        }

        this.messageService.deleteFriendsMessages(dto.getRequesterUsername(), dto.getFriendUsername());

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

    @Transactional
    public void deleteUser(String userId) {
        UserEntity userEntity = this.getById(userId);

        Set<UserEntity> friends = userEntity.getFriends();
        for (UserEntity friend : friends) {
            friend.removeFriend(userEntity);
            this.userRepository.save(friend);
        }

        List<ChannelMembershipEntity> membershipEntities = this.channelMembershipService.findAllByUser(userEntity);
        this.channelMembershipService.deleteAll(membershipEntities);

        List<MessageEntity> allUserMessages = this.messageService.getAllUserMessages(userId);
        allUserMessages.forEach(m -> messageService.deleteMessage(m.getId(), userId));

        this.userRepository.deleteUser(userEntity.getId());
    }

    public UserDetailDTO updateProfile(String userId, UpdateProfileDTO dto) {
        UserEntity userEntity = this.getById(userId);

        validateProfileForUpdate(dto, userEntity);

        userEntity.setAge(dto.getAge());
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setLastModified(LocalDateTime.now());

        return converterUtil.convertToDTO(this.userRepository.save(userEntity));
    }

    private void validateProfileForUpdate(UpdateProfileDTO dto, UserEntity userEntity) {
        if (!userEntity.getUsername().equals(dto.getUsername())) {
            Optional<UserEntity> user = this.userRepository.findByUsernameAndIsActiveTrue(dto.getUsername());
            if (user.isPresent()) {
                throw new IllegalArgumentException("Username " + dto.getUsername() + " is already taken");
            } else {
                userEntity.setUsername(dto.getUsername());
            }
        }

        if (!userEntity.getEmail().equals(dto.getEmail())) {
            Optional<UserEntity> user = this.userRepository.findByEmailAndIsActiveTrue(dto.getEmail());
            if (user.isPresent()) {
                throw new IllegalArgumentException("Email " + dto.getEmail() + " is already taken");
            } else {
                userEntity.setEmail(dto.getEmail());
            }
        }

        if (!userEntity.getPhone().equals(dto.getPhone())) {
            Optional<UserEntity> user = this.userRepository.findByPhoneAndIsActiveTrue(dto.getPhone());
            if (user.isPresent()) {
                throw new IllegalArgumentException("Phone " + dto.getPhone() + " is already taken");
            } else {
                userEntity.setPhone(dto.getPhone());
            }
        }
    }

    public void changePassword(ChangeUserPasswordDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = this.getByUsername(userDetails.getUsername());

        if (this.passwordEncoder.matches(dto.getOldPassword(), userEntity.getPassword())) {
            userEntity.setLastModified(LocalDateTime.now());
            userEntity.setPassword(this.passwordEncoder.encode(dto.getNewPassword()));
            this.userRepository.save(userEntity);
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }
}
