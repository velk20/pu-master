package org.fmi.stream_line.services;

import org.fmi.stream_line.entities.ChannelEntity;
import org.fmi.stream_line.repositories.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<ChannelEntity> getAllChannelsByMember(String userId) {
        return this.channelRepository.findAllByUserIdAndDeletedFalse(userId);
    }
}
