package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.FriendshipRequestEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserFriendshipRequestEntityRepository extends JpaRepository<FriendshipRequestEntity, UUID> {
	@Query("SELECT r FROM FriendshipRequestEntity r WHERE r.receiver = :currentUserId")
	List<FriendshipRequestEntity> getIncomingRequests(UserEntity currentUserId);

	void deleteById(UUID requestId);

	FriendshipRequestEntity getRequestById(UUID requestId);

}
