package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.CommentEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, UUID> {

	Optional<CommentEntity> findById(UUID id);

}
