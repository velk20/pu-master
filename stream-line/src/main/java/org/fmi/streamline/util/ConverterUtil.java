package org.fmi.streamline.util;

import org.fmi.streamline.dtos.channel.ChannelDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.user.UserDetailDTO;
import org.fmi.streamline.dtos.user.UserMembershipDTO;
import org.fmi.streamline.entities.ChannelEntity;
import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.entities.MessageEntity;
import org.fmi.streamline.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConverterUtil {
    private final ModelMapper modelMapper;

    public ConverterUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ChannelDTO convertToChannelDTO(ChannelEntity channelEntity) {
        List<MessageDTO> list = channelEntity.getMessages().stream().map(this::convertToDTO).toList();
        List<UserMembershipDTO> userMembershipDTOS = channelEntity.getMemberships().stream().map(this::convertToDTO).toList();
        ChannelDTO channelDTO = this.convertToDTO(channelEntity);
        channelDTO.setMessages(list);
        channelDTO.setMemberships(userMembershipDTOS);
        return channelDTO;
    }

    public UserMembershipDTO convertToDTO(ChannelMembershipEntity channelMembershipEntity) {
        return  UserMembershipDTO
                .builder()
                .id(channelMembershipEntity.getUser().getId())
                .username(channelMembershipEntity.getUser().getUsername())
                .role(channelMembershipEntity.getRole().name())
                .build();
    }

    public UserDetailDTO convertToDTO(UserEntity userEntity){
        return this.modelMapper.map(userEntity, UserDetailDTO.class);
    }

    public ChannelDTO convertToDTO(ChannelEntity channelEntity) {
        return modelMapper.map(channelEntity, ChannelDTO.class);
    }

    public MessageDTO convertToDTO(MessageEntity messageEntity) {
        return modelMapper.map(messageEntity, MessageDTO.class);
    }
}
