package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelMembershipRepository extends JpaRepository<ChannelMembershipEntity, String> {
    Optional<ChannelMembershipEntity> findByChannelIdAndUserUsername(String channelId, String username);
}
