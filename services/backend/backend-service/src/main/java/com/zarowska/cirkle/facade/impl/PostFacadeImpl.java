package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.api.model.PostsPage;
import com.zarowska.cirkle.api.model.UpdatePostRequest;
import com.zarowska.cirkle.domain.entity.PostEntity;
import com.zarowska.cirkle.domain.entity.PostImage;
import com.zarowska.cirkle.domain.entity.UserEntity;
import com.zarowska.cirkle.domain.service.PostService;
import com.zarowska.cirkle.domain.service.UserService;
import com.zarowska.cirkle.exception.AccessDeniedException;
import com.zarowska.cirkle.exception.BadRequestException;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.PostFacade;
import com.zarowska.cirkle.facade.mapper.PostEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.net.URI;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {

	private final PostService postService;
	private final PostEntityMapper postMapper;
	private final UserService userService;

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

	@Override
	public Post getUserPostByPostId(UUID userId, UUID postId) {
		PostEntity postEntity = postService.findById(postId)
				.orElseThrow(() -> new BadRequestException("Post not found with id=" + postId));

		return postMapper.toDto(postEntity);
	}

	@Override
	public Post updatePostById(UUID userId, UUID postId, UpdatePostRequest updatePostRequest) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());

		// Check if the current user is the same as the one in the parameter
		if (!user.getId().equals(userId)) {
			throw new ResourceNotFoundException("User", Map.of("id", userId));
		}

		// Find the post and check the author's id
		PostEntity updatedPost = postService.findById(postId).map(postEntity -> {
			// Check if the user trying to update the post is the author
			if (!postEntity.getAuthor().getId().equals(userId)) {
				throw new AccessDeniedException("Only the original author of this post has permission to make updates");
			}

			if (updatePostRequest.getText() != null) {
				postEntity.setText(updatePostRequest.getText());
			}

			if (updatePostRequest.getImages() != null) {
				List<URI> imagesListURI = updatePostRequest.getImages();
//				URI testURL= imagesListURI.get(0);
//				PostImage postImage  = new PostImage();
				List<PostImage> test = new ArrayList<>();
				postEntity.setImages(test);
			}

			return postEntity;
		}).orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("id", postId)));

		return postMapper.toDto(updatedPost);
	}




	@Override
	public PostsPage listPostsByUserId(UUID userId, Integer page, Integer size) {
		UserEntity user = userService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
		Integer realPage = page == null ? 0 : page;
		Integer realSize = size == null ? 10 : size;

		Page<PostEntity> postPage = postService.findByUserId(user.getId(), PageRequest.of(realPage, realSize));

		return new PostsPage().totalElements(postPage.getTotalElements()).last(postPage.isLast())
				.first(postPage.isFirst()).size(postPage.getSize()).empty(postPage.isEmpty())
				.number(postPage.getNumber()).numberOfElements(postPage.getNumberOfElements())
				.totalPages(postPage.getTotalPages())
				.content(postPage.getContent().stream().map(postMapper::toDto).toList());

	}

	@Override
	public void deleteUserPostById(UUID userId, UUID postId) {
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());

		if (!user.getId().equals(userId)) {
			throw new ResourceNotFoundException("User", Map.of("id", userId));
		}

		PostEntity postEntity = postService.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("id", postId)));

		if (!postEntity.getAuthor().getId().equals(userId)) {
			throw new AccessDeniedException("Only the original author of this post has permission to delete it");
		}

		postService.delete(postEntity);
	}

}
