package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    List<MessageEntity> findAllByChannelIsNullAndReceiverIdAndAuthorId(String receiverId, String authorId);

}
