package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelMembershipRepository extends JpaRepository<ChannelMembershipEntity, String> {
}
