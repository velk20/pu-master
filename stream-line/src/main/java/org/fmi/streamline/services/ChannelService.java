package org.fmi.streamline.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.fmi.streamline.dtos.channel.AddUserToChannelDTO;
import org.fmi.streamline.dtos.channel.ChannelDTO;
import org.fmi.streamline.dtos.channel.UpdateUserRoleToChannelDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.user.UserDTO;
import org.fmi.streamline.entities.ChannelEntity;
import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.ChannelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ChannelService {
    private final ModelMapper modelMapper;
    private final ChannelRepository channelRepository;
    private final UserService userService;
    private final ChannelMembershipService channelMembershipService;

    public ChannelService(ModelMapper modelMapper, ChannelRepository channelRepository, UserService userService, ChannelMembershipService channelMembershipService) {
        this.modelMapper = modelMapper;
        this.channelRepository = channelRepository;
        this.userService = userService;
        this.channelMembershipService = channelMembershipService;
    }

    public ChannelEntity getById(String id) {
        return this.channelRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Channel with id " + id + " not found"));
    }

    public List<ChannelDTO> getAllChannelsByMember(String userId) {
        this.userService.getById(userId);

        List<ChannelEntity> allByUserIdAndDeletedFalse = this.channelRepository.findAllByUserIdAndDeletedFalse(userId);

        List<ChannelDTO> channelsList = new ArrayList<>();
        for (ChannelEntity channelEntity : allByUserIdAndDeletedFalse) {
            ChannelDTO channelDTO = this.convertToChannelDTO(channelEntity);
            channelsList.add(channelDTO);
        }

        return channelsList;
    }

    private ChannelDTO convertToChannelDTO(ChannelEntity channelEntity) {
        List<MessageDTO> list = channelEntity.getMessages().stream().map(this::convertToDTO).toList();
        List<UserDTO> userDTOS = channelEntity.getMemberships().stream().map(this::convertToDTO).toList();
        ChannelDTO channelDTO = this.convertToDTO(channelEntity);
        channelDTO.setMessages(list);
        channelDTO.setMemberships(userDTOS);
        return channelDTO;
    }

    private UserDTO convertToDTO(ChannelMembershipEntity channelMembershipEntity) {
        return  new UserDTO()
                .builder()
                .id(channelMembershipEntity.getUser().getId())
                .username(channelMembershipEntity.getUser().getUsername())
                .role(channelMembershipEntity.getRole().name())
                .build();
    }

    public ChannelDTO convertToDTO(ChannelEntity channelEntity) {
        return modelMapper.map(channelEntity, ChannelDTO.class);
    }



    public MessageDTO convertToDTO(MessageEntity messageEntity) {
        return modelMapper.map(messageEntity, MessageDTO.class);
    }

    @Transactional
    public ChannelDTO createChannel(ChannelDTO channelDTO) {
        UserEntity userEntity = userService.getById(channelDTO.getOwnerId());

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

    public List<ChannelDTO> getAll() {
        List<ChannelEntity> all = this.channelRepository.findAll();

        List<ChannelDTO> channelsList = new ArrayList<>();
        for (ChannelEntity channelEntity : all) {
            ChannelDTO channelDTO = this.convertToChannelDTO(channelEntity);
            channelsList.add(channelDTO);
        }

        return channelsList;
    }

    public void deleteChannel(String id) {
        ChannelEntity channelEntity = this.channelRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Channel with id " + id + " not found"));

        channelEntity.setDeleted(true);

        this.channelRepository.save(channelEntity);
    }

    @Transactional
    public ChannelDTO addNewUser(AddUserToChannelDTO dto) {
        UserEntity userEntity = this.userService.getByUsername(dto.getUsername());
        ChannelEntity channelEntity = this.getById(dto.getChannelId());

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
        UserEntity user = this.userService.getByUsername(dto.getUsername());
        ChannelEntity channelEntity = this.getById(dto.getChannelId());

        ChannelMembershipEntity entity = channelEntity.getMemberships()
                .stream()
                .filter(m -> m.getUser().getUsername().equals(dto.getUsername()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User with that username is not part of this channel"));

        entity.setRole(ChannelMembershipEntity.Role.valueOf(dto.getRole()));

        ChannelMembershipEntity save = this.channelMembershipService.save(entity);

        ChannelEntity updated = this.channelRepository.save(channelEntity);

        return this.convertToChannelDTO(updated);
    }
}
