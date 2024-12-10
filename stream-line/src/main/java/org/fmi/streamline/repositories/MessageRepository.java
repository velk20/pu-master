package org.fmi.streamline.repositories;

import org.fmi.streamline.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {
}
