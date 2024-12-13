package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    @Query("""
    SELECT m FROM MessageEntity m 
    WHERE m.channel IS NULL 
      AND (
        (m.receiver.id = :receiverId AND m.author.id = :authorId) 
        OR (m.receiver.id = :authorId AND m.author.id = :receiverId)
      )
""")
    List<MessageEntity> findMessagesByChannelNullAndReceiverAuthorSwapped(
            @Param("receiverId") String receiverId,
            @Param("authorId") String authorId
    );
}
