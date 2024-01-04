package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

	@Query("SELECT u FROM UserEntity u WHERE u.profile.email = :email")
	Optional<UserEntity> findByProfileEmail(String email);

}