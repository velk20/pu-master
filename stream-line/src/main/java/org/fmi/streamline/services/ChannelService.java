package org.fmi.streamline.services;

import jakarta.transaction.Transactional;
import org.fmi.streamline.dtos.channel.*;
import org.fmi.streamline.dtos.message.FriendMessageDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.message.SendMessageToFriendDTO;
import org.fmi.streamline.dtos.user.UserDetailDTO;
import org.fmi.streamline.dtos.user.UserMembershipDTO;
import org.fmi.streamline.entities.ChannelEntity;
import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.ChannelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChannelService {
    private final ModelMapper modelMapper;
    private final ChannelRepository channelRepository;
    private final UserService userService;
    private final MessageService messageService;
    private final ChannelMembershipService channelMembershipService;

    public ChannelService(ModelMapper modelMapper, ChannelRepository channelRepository, UserService userService, MessageService messageService, ChannelMembershipService channelMembershipService) {
        this.modelMapper = modelMapper;
        this.channelRepository = channelRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.channelMembershipService = channelMembershipService;
    }

    public ChannelEntity getById(String id) {
        return this.channelRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Channel with id " + id + " not found"));
    }

    public Optional<ChannelEntity> getByName(String name) {
        return this.channelRepository.findByNameAndDeletedFalse(name);
    }

    public List<ChannelDTO> getAll() {
        return this.channelRepository.findAll().stream().map(this::convertToChannelDTO).toList();
    }

    public List<ChannelDTO> getAllChannelsByMember(String userId) {
        this.userService.getById(userId);

        return this.channelRepository.findAllByUserIdAndDeletedFalse(userId).stream().map(this::convertToChannelDTO).toList();
    }

    @Transactional
    public ChannelDTO createChannel(ChannelDTO channelDTO) {
        UserEntity userEntity = userService.getByUsername(channelDTO.getOwnerUsername());
        validateChannelName(channelDTO.getName());

        ChannelEntity channelEntity = ChannelEntity.builder()
                .createdAt(LocalDateTime.now())
                .owner(userEntity)
                .memberships(new HashSet<>())
                .messages(new HashSet<>())
                .name(channelDTO.getName())
                .build();

        ChannelEntity newChannel = this.channelRepository.save(channelEntity);

        ChannelMembershipEntity channelMembershipEntity = ChannelMembershipEntity.builder()
                .role(ChannelMembershipEntity.Role.OWNER)
                .user(userEntity)
                .channel(newChannel)
                .build();

        ChannelMembershipEntity newChannelMembership = this.channelMembershipService.create(channelMembershipEntity);

        ChannelEntity channel = newChannel.addMembership(newChannelMembership);
        this.channelRepository.save(channel);

        return this.convertToChannelDTO(channel);
    }

    public void deleteChannel(String id) {
        ChannelEntity channelEntity = this.getById(id);
        channelEntity.setDeleted(true);

        this.channelRepository.save(channelEntity);
    }

    @Transactional
    public ChannelDTO addNewUser(AddOrRemoveUserToChannelDTO dto) {
        UserEntity userEntity = this.userService.getByUsername(dto.getUsername());
        ChannelEntity channelEntity = this.getById(dto.getChannelId());

        Optional<ChannelMembershipEntity> userPartOfChannel = this.isUserPartOfChannel(channelEntity, dto.getUsername());

        if (userPartOfChannel.isPresent()) {
            throw new IllegalArgumentException("User already in the channel");
        }

        ChannelMembershipEntity entity = ChannelMembershipEntity.builder()
                .channel(channelEntity)
                .user(userEntity)
                .role(ChannelMembershipEntity.Role.GUEST)
                .build();

        ChannelMembershipEntity newMember = this.channelMembershipService.create(entity);

        channelEntity.addMembership(newMember);

        ChannelEntity save = this.channelRepository.save(channelEntity);
        return this.convertToChannelDTO(save);
    }

    @Transactional
    public ChannelDTO updateUserRoleInChannel(UpdateUserRoleToChannelDTO dto) {
        this.userService.getByUsername(dto.getUsername());
        ChannelEntity channelEntity = this.getById(dto.getChannelId());

        Optional<ChannelMembershipEntity> userPartOfChannel = this.isUserPartOfChannel(channelEntity, dto.getUsername());
        if (userPartOfChannel.isEmpty()){
            throw new IllegalArgumentException("User is not part of this channel");
        }

        ChannelMembershipEntity entity = userPartOfChannel.get();
        entity.setRole(ChannelMembershipEntity.Role.valueOf(dto.getRole()));

        this.channelMembershipService.save(entity);

        ChannelEntity updated = this.channelRepository.save(channelEntity);

        return this.convertToChannelDTO(updated);
    }

    public ChannelDTO addMessageToChannel(AddMessageToChannelDTO dto) {
        UserEntity user = this.userService.getByUsername(dto.getUsername());
        ChannelEntity channel = this.getById(dto.getChannelId());

        Optional<ChannelMembershipEntity> userPartOfChannel = this.isUserPartOfChannel(channel, dto.getUsername());
        if (userPartOfChannel.isEmpty()){
            throw new IllegalArgumentException("User is not part of this channel");
        }

        MessageEntity messageEntity = MessageEntity
                .builder()
                .author(user)
                .channel(channel)
                .content(dto.getMessage())
                .timestamp(LocalDateTime.now())
                .deleted(false)
                .receiver(null)
                .build();

        this.messageService.save(messageEntity);

        return this.convertToChannelDTO(this.getById(dto.getChannelId()));
    }

    public MessageDTO sendMessageToFriend(SendMessageToFriendDTO dto) {
        UserEntity receiverUser = userService.getById(dto.getFriendId());
        UserEntity senderUser = userService.getById(dto.getSenderId());

        MessageEntity newMessage = MessageEntity.builder()
                .receiver(receiverUser)
                .channel(null)
                .content(dto.getMessage())
                .timestamp(LocalDateTime.now())
                .deleted(false)
                .author(senderUser)
                .build();

        MessageEntity saved = this.messageService.save(newMessage);
        return this.convertToDTO(saved);
    }

    public List<MessageDTO> getFriendMessages(FriendMessageDTO dto) {
        return this.messageService.getAllMessagesForFriend(dto).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ChannelDTO updateChannel(UpdateChannelDTO dto) {
        String currentLoggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        ChannelEntity channelEntity = this.getById(dto.getId());
        validateChannelForUpdate(dto, channelEntity, currentLoggedUsername);

        channelEntity.setName(dto.getName());

        this.channelRepository.save(channelEntity);
        return this.convertToChannelDTO(channelEntity);
    }

    public List<UserDetailDTO> getAllAvailableUsersToAddToChannel(String channelId) {
        this.getById(channelId);

        List<UserEntity> usersNotInChannel = this.userService.findUsersNotInChannel(channelId);
        return usersNotInChannel.stream().map(u -> this.modelMapper.map(u, UserDetailDTO.class)).toList();
    }

    public ChannelDTO removeUserFromChannel(AddOrRemoveUserToChannelDTO dto) {         ;
        ChannelEntity channelEntity = this.getById(dto.getChannelId());
        Optional<ChannelMembershipEntity> userPartOfChannel = this.isUserPartOfChannel(channelEntity, dto.getUsername());
        if (userPartOfChannel.isEmpty()){
            throw new IllegalArgumentException("User is not part of this channel");
        }
        ChannelMembershipEntity membershipEntity = userPartOfChannel.get();
        channelEntity.removeMembership(membershipEntity);
        ChannelMembershipEntity saved = this.channelMembershipService.save(membershipEntity);

        return this.convertToChannelDTO(saved.getChannel());
    }


    private void validateChannelForUpdate(UpdateChannelDTO dto, ChannelEntity channelEntity, String currentLoggedUsername) {
        Optional<ChannelMembershipEntity> userPartOfChannel = isUserPartOfChannel(channelEntity, currentLoggedUsername);
        if (userPartOfChannel.isEmpty()){
            throw new IllegalArgumentException("User is not part of this channel");
        }

        if (!isUserOwnerOrAdminOfChannel(userPartOfChannel.get())){
            throw new IllegalArgumentException("User is not owner or admin of this channel");
        }

        validateChannelName(dto.getName());
    }


    private void validateChannelName(String channelDTO) {
        Optional<ChannelEntity> optionalChannel = this.getByName(channelDTO);
        if (optionalChannel.isPresent()) {
            throw new IllegalArgumentException("Channel with name: " + channelDTO + " already exists");
        }
    }

    private Optional<ChannelMembershipEntity> isUserPartOfChannel(ChannelEntity channelEntity, String username){
       return channelEntity.getMemberships()
                .stream()
                .filter(m -> m.getUser().getUsername().equals(username))
                .findFirst();
    }

    private boolean isUserOwnerOrAdminOfChannel(ChannelMembershipEntity membership){
        return membership.getRole().equals(ChannelMembershipEntity.Role.OWNER)
                || membership.getRole().equals(ChannelMembershipEntity.Role.ADMIN);
    }

    private ChannelDTO convertToChannelDTO(ChannelEntity channelEntity) {
        List<MessageDTO> list = channelEntity.getMessages().stream().map(this::convertToDTO).toList();
        List<UserMembershipDTO> userMembershipDTOS = channelEntity.getMemberships().stream().map(this::convertToDTO).toList();
        ChannelDTO channelDTO = this.convertToDTO(channelEntity);
        channelDTO.setMessages(list);
        channelDTO.setMemberships(userMembershipDTOS);
        return channelDTO;
    }

    private UserMembershipDTO convertToDTO(ChannelMembershipEntity channelMembershipEntity) {
        return  UserMembershipDTO
                .builder()
                .id(channelMembershipEntity.getUser().getId())
                .username(channelMembershipEntity.getUser().getUsername())
                .role(channelMembershipEntity.getRole().name())
                .build();
    }

    private ChannelDTO convertToDTO(ChannelEntity channelEntity) {
        return modelMapper.map(channelEntity, ChannelDTO.class);
    }

    private MessageDTO convertToDTO(MessageEntity messageEntity) {
        return modelMapper.map(messageEntity, MessageDTO.class);
    }
}
