package com.zarowska.cirkle.domain.service;

import com.zarowska.cirkle.domain.entity.CommentEntity;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {

	CommentEntity save(CommentEntity commentEntity);
	Optional<CommentEntity> findById(UUID commentId);
}
