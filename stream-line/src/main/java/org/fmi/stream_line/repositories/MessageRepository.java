package org.fmi.stream_line.repositories;

import org.fmi.stream_line.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {
}
