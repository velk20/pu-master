package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    List<MessageEntity> findByAuthorId(String userId);
    Optional<MessageEntity> findByIdAndDeletedFalse(String messageId);
    @Query("""
    SELECT m FROM MessageEntity m
    WHERE m.channel IS NULL AND m.deleted = false
      AND (
        (m.receiver.id = :receiverId AND m.author.id = :authorId)
        OR (m.receiver.id = :authorId AND m.author.id = :receiverId)
      )
""")
    List<MessageEntity> findMessagesByChannelNullAndReceiverAuthorSwapped(
            @Param("receiverId") String receiverId,
            @Param("authorId") String authorId
    );

    @Modifying
    @Query("""
    UPDATE MessageEntity m
    SET m.deleted = true
    WHERE m.channel IS NULL AND (
        (m.receiver.username = :receiverUsername AND m.author.username = :authorUsername)
        OR (m.receiver.username = :authorUsername AND m.author.username = :receiverUsername)
      )
""")
    void deleteMessagesBetweenFriends(@Param("authorUsername") String authorUsername, @Param("receiverUsername") String receiverUsername);
}
