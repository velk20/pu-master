package org.fmi.streamline.services;

import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.repositories.ChannelMembershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelMembershipService {
    private final ChannelMembershipRepository channelMembershipRepository;

    public ChannelMembershipService(ChannelMembershipRepository channelMembershipRepository) {
        this.channelMembershipRepository = channelMembershipRepository;
    }
    public Optional<ChannelMembershipEntity> findByUsernameAndChannelId(String username, String channelId){
        return this.channelMembershipRepository.findByChannelIdAndUserUsername(channelId, username);
    }

    public ChannelMembershipEntity create(ChannelMembershipEntity channelMembershipEntity) {
        return this.channelMembershipRepository.save(channelMembershipEntity);
    }

    public ChannelMembershipEntity save(ChannelMembershipEntity entity) {
        return this.channelMembershipRepository.save(entity);
    }

    public List<ChannelMembershipEntity> findAllByUser(UserEntity userEntity){
        return this.channelMembershipRepository.findAllByUserId(userEntity.getId());
    }

    public void deleteAll(List<ChannelMembershipEntity> membershipEntities) {
        this.channelMembershipRepository.deleteAll(membershipEntities);
    }
}
