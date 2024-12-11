package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {
    Optional<ChannelEntity> findByNameAndDeletedFalse(String name);
    Optional<ChannelEntity> findByIdAndDeletedFalse(String id);

    @Query("SELECT c FROM ChannelEntity c " +
            "JOIN c.memberships cm " +
            "WHERE cm.user.id = :userId")
    List<ChannelEntity> findAllByUserIdAndDeletedFalse(String userId);

}
