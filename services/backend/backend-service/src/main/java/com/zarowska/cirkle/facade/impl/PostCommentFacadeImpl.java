package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import com.zarowska.cirkle.domain.entity.CommentEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.CommentService;
import com.zarowska.cirkle.exception.BadRequestException;
import com.zarowska.cirkle.facade.PostCommentFacade;
import com.zarowska.cirkle.facade.mapper.CommentEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentFacadeImpl implements PostCommentFacade {

	private final CommentService commentService;
	private final CommentEntityMapper commentEntityMapper;

	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public Comment createPostComment(UUID userId, UUID postId, CreateCommentRequest createCommentRequest) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());

		if (!user.getId().equals(userId)) {
			throw new BadRequestException("Not allowed to create post for user id=" + userId);
		}

		CommentEntity newCommentEntity = commentService
				.save(new CommentEntity().setAuthor(user).setText(createCommentRequest.getText()));

		return commentEntityMapper.toDto(newCommentEntity);
	}
}
