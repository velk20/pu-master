package org.fmi.streamline.services;

import org.fmi.streamline.dtos.channel.ChannelDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.user.UserDTO;
import org.fmi.streamline.entities.ChannelEntity;
import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.repositories.ChannelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    private final ModelMapper modelMapper;
    private final ChannelRepository channelRepository;
    private final UserService userService;

    public ChannelService(ModelMapper modelMapper, ChannelRepository channelRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.channelRepository = channelRepository;
        this.userService = userService;
    }

    public List<ChannelDTO> getAllChannelsByMember(String userId) {
        this.userService.getById(userId);

        List<ChannelEntity> allByUserIdAndDeletedFalse = this.channelRepository.findAllByUserIdAndDeletedFalse(userId);

        List<ChannelDTO> channelsList = new ArrayList<>();
        for (ChannelEntity channelEntity : allByUserIdAndDeletedFalse) {
            List<MessageDTO> list = channelEntity.getMessages().stream().map(this::convertToDTO).toList();
            List<UserDTO> userDTOS = channelEntity.getMemberships().stream().map(this::convertToDTO).toList();
            ChannelDTO channelDTO = this.convertToDTO(channelEntity);
            channelDTO.setMessages(list);
            channelDTO.setMemberships(userDTOS);
            channelsList.add(channelDTO);
        }

        return channelsList;
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

    public ChannelDTO createChannel(ChannelDTO channelDTO) {
        UserEntity userEntity = userService.getById(channelDTO.getOwnerId());

        ChannelEntity channelEntity = ChannelEntity.builder()
                .createdAt(LocalDateTime.now())
                .owner(userEntity)
                .name(channelDTO.getName())
                .build();

        ChannelEntity newChannel = this.channelRepository.save(channelEntity);

        return this.convertToDTO(newChannel);
    }
}
