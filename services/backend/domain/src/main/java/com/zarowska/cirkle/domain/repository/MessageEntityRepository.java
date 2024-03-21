package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.MessageEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, UUID> {

	@Query("SELECT distinct m FROM MessageEntity m WHERE "
			+ "(m.sender.id = : currentUserId OR  m.receiver.id = : userId) OR "
			+ "(m.receiver.id = : currentUserId OR  m.sender.id = : userId) " + "order by m.createdAt")
	Page<MessageEntity> findByUsersId(UUID currentUserId, UUID userId, PageRequest pageRequest);

}
