package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.request.CreateCommentRequest;
import blog.cirkle.domain.model.request.CreatePostRequest;
import blog.cirkle.domain.model.request.UpdateCommentRequest;
import blog.cirkle.domain.model.response.CommentDto;
import blog.cirkle.domain.model.response.PostDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface PostFacade {
	@Transactional(readOnly = true)
	Page<PostDto> listByUserId(UUID userId, Pageable pageable);

	@Transactional
	PostDto createOne(UUID userId, CreatePostRequest request);

	@Transactional(readOnly = true)
	Page<PostDto> feed(Pageable pageable);

	@Transactional(readOnly = true)
	PostDto findByUserId(UUID userId, UUID postId);

	@Transactional
	void deleteByUserId(UUID userId, UUID postId);

	@Transactional
	CommentDto addComment(UUID userId, UUID postId, CreateCommentRequest commentDto);

	@Transactional
	CommentDto addComment(UUID userId, UUID postId, UUID commentId, CreateCommentRequest commentDto);

	CommentDto findCommentById(UUID userId, UUID postId, UUID commentId, Pageable pageable);

	@Transactional
	CommentDto updateComment(UUID userId, UUID postId, UUID commentId, UpdateCommentRequest commentDto);

	@Transactional(readOnly = true)
	Page<CommentDto> listComments(UUID userId, UUID postId, Pageable pageable);

	@Transactional
	void deleteComment(UUID userId, UUID postId, UUID commentId);
}
