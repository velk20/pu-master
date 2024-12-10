package org.fmi.stream_line.services;

import org.fmi.stream_line.dtos.ChannelDTO;
import org.fmi.stream_line.dtos.MessageDTO;
import org.fmi.stream_line.dtos.UserDTO;
import org.fmi.stream_line.entities.ChannelEntity;
import org.fmi.stream_line.entities.ChannelMembershipEntity;
import org.fmi.stream_line.entities.MessageEntity;
import org.fmi.stream_line.entities.UserEntity;
import org.fmi.stream_line.repositories.ChannelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
