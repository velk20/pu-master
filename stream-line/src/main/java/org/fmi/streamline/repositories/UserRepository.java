package org.fmi.streamline.repositories;

import jakarta.validation.constraints.NotEmpty;
import org.fmi.streamline.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT u FROM UserEntity u " +
            "WHERE u.id != :userId " +
            "AND u NOT IN (SELECT f FROM UserEntity currentUser JOIN currentUser.friends f WHERE currentUser.id = :userId)")
    List<UserEntity> findAvailableFriends(@Param("userId") String userId);
    Optional<UserEntity> findByIdAndIsActiveTrue(String id);
    Optional<UserEntity> findByUsernameAndIsActiveTrue(String username);
    Optional<UserEntity> findByEmailAndIsActiveTrue(String email);
    Optional<UserEntity> findByPhoneAndIsActiveTrue(String phone);
    @Query("SELECT u FROM UserEntity u WHERE u.id NOT IN " +
            "(SELECT cm.user.id FROM ChannelMembershipEntity cm WHERE cm.channel.id = :channelId)")
    List<UserEntity> findUsersNotInChannel(@Param("channelId") String channelId);
@Query("SELECT cm.user FROM ChannelMembershipEntity cm " +
        "WHERE cm.channel.id = :channelId AND cm.user.username = :username")
    Optional<UserEntity> findUserInChannel(String username, String channelId);
}
