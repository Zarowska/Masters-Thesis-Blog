package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.PostEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, UUID> {
	List<PostEntity> findAllByAuthorId(UUID userId);
}
