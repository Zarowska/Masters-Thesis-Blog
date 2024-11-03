package blog.cirkle.app.facade.impl;

import static blog.cirkle.app.utils.ModelUtils.assertAuthoredByCurrentUser;
import static blog.cirkle.app.utils.SecurityUtils.getCurrentUser;

import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.*;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.facade.PostsFacade;
import blog.cirkle.app.model.entity.Comment;
import blog.cirkle.app.model.entity.Image;
import blog.cirkle.app.model.entity.Post;
import blog.cirkle.app.model.entity.Reaction;
import blog.cirkle.app.service.*;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsFacadeImpl implements PostsFacade {

	private final PostService postService;
	private final CommentService commentService;
	private final ModelMapperService mapper;
	private final ReactionService reactionService;
	private final FeedService feedService;
	private final UserService userService;
	@Override
	public PostDto getPostById(UUID postId) {
		return mapper.toPostDto(postService.findById(postId));
	}

	@Override
	public PostDto createPost(CreatePostDto request) {
		Post post = postService.createPost(userService.findById(getCurrentUser().getId()), request);
		feedService.sendToFeeds(post);
		return mapper.toPostDto(post);
	}

	@Override
	public PostDto updatePost(UUID postId, UpdateMediaDto request) {
		Post post = postService.findById(postId);
		assertAuthoredByCurrentUser(post);
		return mapper.toPostDto(postService.updatePost(post, request));
	}

	@Override
	public void deletePostById(UUID postId) {
		Post post = postService.findById(postId);
		assertAuthoredByCurrentUser(post);
		postService.deleteById(post.getId());
	}

	@Override
	public Page<CommentDto> listRootCommentsByPostId(UUID postId, Pageable pageable) {
		postService.findById(postId);
		return commentService.findRootCommentsByPostId(postId, pageable).map(mapper::toCommentDto);
	}

	@Override
	public Page<CommentDto> listCommentsByPostIdAndCommentId(UUID postId, Long commentId, Pageable pageable) {
		postService.findById(postId);
		return commentService.findNestedCommentsByPostId(postId, commentId, pageable).map(mapper::toCommentDto);
	}

	@Override
	public CommentDto updateCommentByPostIdAndCommentId(UUID postId, Long commentId, UpdateCommentDto request) {
		Comment comment = commentService.findByPostIdAndId(postId, commentId);
		assertAuthoredByCurrentUser(comment);
		return mapper.toCommentDto(commentService.updateComment(comment, request));
	}

	@Override
	public void deleteCommentByPostIdAndCommentId(UUID postId, Long commentId) {
		Comment comment = commentService.findByPostIdAndId(postId, commentId);
		assertAuthoredByCurrentUser(comment);
		commentService.deleteByPostIdAndCommentId(postId, commentId);
	}

	@Override
	public CommentDto createComment(UUID postId, Long commentId, CreateCommentDto request) {
		Post post = postService.findById(postId);
		Comment comment = commentService.createComment(getCurrentUser(), post, commentId, request);
		return mapper.toCommentDto(comment);
	}

	@Override
	public ReactionsDto createReactionByPostId(UUID postId, CreateReactionDto request) {
		Post post = postService.findById(postId);
		Collection<Reaction> reactions = reactionService.createReaction(getCurrentUser(), post, request);
		return mapper.toReactionDto(reactions);
	}

	@Override
	public ReactionsDto createReactionByPostIdAndImageId(UUID postId, UUID imageId, CreateReactionDto request) {
		Post post = postService.findById(postId);
		Image image = post.getImages().stream().filter(it -> it.getId().equals(imageId)).findFirst()
				.orElseThrow(() -> NotFoundException.image(imageId));
		return mapper.toReactionDto(reactionService.createReaction(getCurrentUser(), image, request));
	}

	@Override
	public ReactionsDto createReactionByPostIdAndCommentId(UUID postId, Long commentId, CreateReactionDto request) {
		Post post = postService.findById(postId);
		Comment comment = commentService.findByPostIdAndId(postId, commentId);
		return mapper.toReactionDto(reactionService.createReaction(getCurrentUser(), comment, request));
	}

	@Override
	public CommentDto getCommentByPostIdAndCommentId(UUID postId, Long commentId) {
		return mapper.toCommentDto(commentService.findByPostIdAndId(postId, commentId));
	}

}
