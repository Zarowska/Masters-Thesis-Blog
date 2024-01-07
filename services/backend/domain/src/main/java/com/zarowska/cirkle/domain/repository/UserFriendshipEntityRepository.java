package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.FriendshipEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserFriendshipEntityRepository extends JpaRepository<FriendshipEntity, UUID> {

	@Query("DELETE FROM FriendshipEntity f WHERE f.id = :friendId")
	void removeFriend(@Param("friendId") UUID friendId);

	@Query("SELECT f FROM FriendshipEntity f WHERE f.sender.id = :userId")
	List<FriendshipEntity> getUserFriends(@Param("userId") UUID userId);

	@Query("SELECT f FROM FriendshipEntity f WHERE f.receiver.id = :currentUserId AND f.sender.id = :userId")
	FriendshipEntity getUserFriendshipWith(@Param("currentUserId") UUID currentUserId, @Param("userId") UUID userId);

	@Query("SELECT f FROM FriendshipEntity f where f.receiver.id = :userId")
	Page<FriendshipEntity> findAllFriends(UUID userId, Pageable pageRequest);
}
