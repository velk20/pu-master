package org.fmi.stream_line.repositories;

import org.fmi.stream_line.entities.ChannelMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelMembershipRepository extends JpaRepository<ChannelMembershipEntity, String> {
}
