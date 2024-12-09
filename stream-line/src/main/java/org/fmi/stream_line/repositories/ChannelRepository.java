package org.fmi.stream_line.repositories;

import org.fmi.stream_line.entities.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {

    @Query("SELECT c FROM ChannelEntity c " +
            "JOIN c.memberships cm " +
            "WHERE cm.user.id = :userId")
    List<ChannelEntity> findAllByUserIdAndDeletedFalse(String userId);
}
