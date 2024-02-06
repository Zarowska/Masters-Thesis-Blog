package com.zarowska.cirkle.domain.repository;

import com.zarowska.cirkle.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, UUID> {
}
