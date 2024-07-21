package blog.cirkle.domain.facade.impl;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.entity.participant.Relation;
import blog.cirkle.domain.entity.resource.*;
import blog.cirkle.domain.exception.NotAllowedException;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.facade.mappers.UserMapper;
import blog.cirkle.domain.model.request.CreateCommentRequest;
import blog.cirkle.domain.model.request.CreatePostRequest;
import blog.cirkle.domain.model.request.UpdateCommentRequest;
import blog.cirkle.domain.model.response.CommentDto;
import blog.cirkle.domain.model.response.PostDto;
import blog.cirkle.domain.model.response.RelationType;
import blog.cirkle.domain.security.BlogUserDetails;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.*;
import blog.cirkle.domain.service.impl.UserServiceHolder;
import java.util.*;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {

	private final PostService postService;
	private final FileService fileService;
	private final CommentService commentService;
	private final RelationService relationService;
	private final UserMapper userMapper;
	private final ResourceService resourceService;

	@Override
	public Page<PostDto> listByUserId(UUID userId, Pageable pageable) {

		Page<Post> posts = postService.listByUserId(userId, pageable);

		return posts.map(this::toDto);

	}

	@Override
	public PostDto createOne(UUID userId, CreatePostRequest request) {
		Post post = resourceService.prepareResource(new Post(), request);
		final Post savedPost = postService.save(post);
		addToFeeds(userId, savedPost);
		return toDto(savedPost);
	}

	private void addToFeeds(UUID userId, Post savedPost) {
		List<Relation> relations;
		int page = 0;
		do {
			relations = relationService
					.findRelationsByType(userId, Set.of(RelationType.FOLLOWER), Pageable.ofSize(100).withPage(page))
					.getContent();
			relations.forEach(rel -> postService.addToFeed(rel.getRelated(), savedPost.getId()));
			page++;
		} while (!relations.isEmpty());
	}

	@Override
	public Page<PostDto> feed(Pageable pageable) {
		Page<Post> feed = postService.getFeedByUserId(UserServiceHolder.currentUserOrNull().getId(), pageable);

		return feed.map(it -> toDto(it));
	}

	@Override
	public PostDto findByUserId(UUID userId, UUID postId) {
		Post post = postService.findByUserId(userId, postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("userId", userId, "postId", postId)));
		return toDto(post);
	}

	@Override
	public void deleteByUserId(UUID userId, UUID postId) {
		Post post = postService.findByUserId(userId, postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("userId", userId, "postId", postId)));

		BlogUserDetails currentUser = UserContextHolder.getCurrentUser().get();
		if (currentUser.isAdmin() || post.getAuthor().getId().equals(currentUser.getId())) {
			postService.deletePostById(userId, postId);
		} else {
			throw new NotAllowedException("Only author can delete this post");
		}
	}

	CommentDto toDto(Comment comment) {
		List<Comment> children = commentService.findByParent(comment);
		return new CommentDto().setId(comment.getId()).setCreatedAt(comment.getCreatedAt().getEpochSecond())
				.setUpdatedAt(comment.getUpdatedAt().getEpochSecond())
				.setAuthor(userMapper.toUserDto(comment.getAuthor()))
				.setChildComments(children.stream().map(it -> toDto(it)).toList()).setText(getTextPart(comment))
				.setImages(getImages(comment));
	}

	PostDto toDto(Post post) {
		return new PostDto().setId(post.getId()).setSlug("").setCreatedAt(post.getCreatedAt().getEpochSecond())
				.setUpdatedAt(post.getUpdatedAt().getEpochSecond()).setAuthor(userMapper.toUserDto(post.getAuthor()))
				.setText(getTextPart(post)).setImages(getImages(post));

	}

	@Override
	public CommentDto addComment(UUID userId, UUID postId, CreateCommentRequest commentDto) {
		return addComment(userId, postId, null, commentDto);
	}

	@Override
	public CommentDto addComment(UUID userId, UUID postId, UUID commentId, CreateCommentRequest commentDto) {
		Post post = postService.findByUserId(userId, postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("userId", userId, "postId", postId)));

		Comment parentComment = Optional.ofNullable(commentId)
				.flatMap(it -> commentService.findById(userId, postId, it)).orElse(null);

		Comment comment = resourceService.prepareResource(new Comment().setPost(post).setParent(parentComment),
				commentDto);
		return toDto(commentService.save(comment));
	}

	@Override
	public CommentDto findCommentById(UUID userId, UUID postId, UUID commentId, Pageable pageable) {
		Post post = postService.findByUserId(userId, postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("userId", userId, "postId", postId)));

		Comment comment = commentService.findById(userId, postId, commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", Map.of("commentId", commentId)));

		return toDto(comment);
	}

	@Override
	public CommentDto updateComment(UUID userId, UUID postId, UUID commentId, UpdateCommentRequest commentDto) {
		Post post = postService.findByUserId(userId, postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("userId", userId, "postId", postId)));

		Comment comment = commentService.findById(userId, postId, commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", Map.of("commentId", commentId)));

		BlogUserDetails currentUser = UserContextHolder.getCurrentUser().get();

		if (currentUser.isAdmin() || comment.getAuthor().getId().equals(currentUser.getId())) {
			Comment newComment = resourceService.prepareResource(new Comment(), commentDto);

			Comment updatedComment = resourceService.updateResource(comment, newComment);

			return toDto(resourceService.save(updatedComment));

		} else {
			throw new NotAllowedException("Only author can update this comment");
		}

	}

	@Override
	public Page<CommentDto> listComments(UUID userId, UUID postId, Pageable pageable) {
		return commentService.findByPostId(userId, postId, pageable).map(it -> toDto(it));
	}

	@Override
	public void deleteComment(UUID userId, UUID postId, UUID commentId) {
		Comment comment = commentService.findById(userId, postId, commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", Map.of("commentId", userId)));

		BlogUserDetails currentUser = UserContextHolder.getCurrentUser().get();

		if (currentUser.isAdmin() || comment.getAuthor().getId().equals(currentUser.getId())) {
			commentService.deleteById(commentId);
		} else {
			throw new NotAllowedException("Only author can update this comment");
		}
	}

	private List<UUID> getImages(Resource resource) {
		return filterContent(Image.class, resource.getContent()).map(BaseEntity::getId).toList();
	}

	private String getTextPart(Resource resource) {
		return filterContent(Text.class, resource.getContent()).findFirst().map(Text::getText).orElse("");

	}

	private <T> Stream<T> filterContent(Class<T> clazz, Collection objects) {
		return objects.stream().filter(it -> it.getClass().isAssignableFrom(clazz)).map(it -> (T) it);
	}

}
