package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.MessageEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, UUID> {
}
