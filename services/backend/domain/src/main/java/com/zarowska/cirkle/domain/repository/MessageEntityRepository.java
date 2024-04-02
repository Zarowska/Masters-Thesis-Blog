package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.MessageEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, UUID> {

	@Query("SELECT distinct m FROM MessageEntity m WHERE "
			+ "(m.sender.id = :currentUserId  AND m.receiver.id = :userId) "
			+ "or (m.receiver.id = :currentUserId  AND m.sender.id = :userId) " + "order by m.createdAt")
	Page<MessageEntity> findByUsersId(@Param("currentUserId") UUID currentUserId, @Param("userId") UUID userId,
			Pageable pageRequest);

	@Query("SELECT distinct m FROM MessageEntity m WHERE m.receiver.id = :currentUserId and m.viewedByReceiver = FALSE order by m.createdAt")
	List<MessageEntity> findUnreadMessagesByUserId(@Param("currentUserId") UUID currentUserId);

	@Query("SELECT distinct m FROM MessageEntity m WHERE m.id = :messageId and m.viewedByReceiver IS FALSE order by m.createdAt")
	MessageEntity findUnreadMessagesById(UUID messageId);
}
