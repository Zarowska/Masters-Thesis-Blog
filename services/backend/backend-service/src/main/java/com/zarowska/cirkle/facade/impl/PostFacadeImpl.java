package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.domain.entity.PostEntity;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.PostService;
import com.zarowska.cirkle.exception.BadRequestException;
import com.zarowska.cirkle.facade.PostFacade;
import com.zarowska.cirkle.facade.mapper.PostEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {

	private final PostService postService;
	private final PostEntityMapper postMapper;

	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public Post createPost(UUID userId, CreatePostRequest createPostRequest) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());

		if (!user.getId().equals(userId)) {
			throw new BadRequestException("Not allowed to create post for user id=" + userId);
		}

		PostEntity newPostEntity = new PostEntity().setAuthor(user).setText(createPostRequest.getText());

		return postMapper.toDto(postService.save(newPostEntity));
	}
}
