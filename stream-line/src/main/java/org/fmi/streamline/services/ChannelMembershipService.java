package org.fmi.streamline.services;

import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.repositories.ChannelMembershipRepository;
import org.springframework.stereotype.Service;

@Service
public class ChannelMembershipService {
    private final ChannelMembershipRepository channelMembershipRepository;

    public ChannelMembershipService(ChannelMembershipRepository channelMembershipRepository) {
        this.channelMembershipRepository = channelMembershipRepository;
    }


    public ChannelMembershipEntity create(ChannelMembershipEntity channelMembershipEntity) {
        return this.channelMembershipRepository.save(channelMembershipEntity);
    }
}
