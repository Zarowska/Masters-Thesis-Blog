package blog.cirkle.app.facade;

import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.*;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PostsFacade {

	PostDto getPostById(UUID postId);

	@Transactional
	PostDto createPost(CreatePostDto request);

	@Transactional
	PostDto updatePost(UUID postId, UpdateMediaDto request);

	@Transactional
	void deletePostById(UUID postId);

	CommentDto getCommentByPostIdAndCommentId(UUID postId, Long commentId);

	Page<CommentDto> listRootCommentsByPostId(UUID postId, Pageable pageable);

	Page<CommentDto> listCommentsByPostIdAndCommentId(UUID postId, Long commentId, Pageable pageable);

	@Transactional
	CommentDto updateCommentByPostIdAndCommentId(UUID postId, Long commentId, UpdateCommentDto request);

	@Transactional
	void deleteCommentByPostIdAndCommentId(UUID postId, Long commentId);

	@Transactional
	CommentDto createComment(UUID postId, Long commentId, CreateCommentDto request);

	@Transactional
	ReactionsDto createReactionByPostId(UUID postId, CreateReactionDto request);

	@Transactional
	ReactionsDto createReactionByPostIdAndImageId(UUID postId, UUID imageId, CreateReactionDto request);

	@Transactional
	ReactionsDto createReactionByPostIdAndCommentId(UUID postId, Long commentId, CreateReactionDto request);
}
