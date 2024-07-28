package blog.cirkle.app.service;

import blog.cirkle.app.api.rest.model.request.CreateCommentDto;
import blog.cirkle.app.api.rest.model.request.UpdateCommentDto;
import blog.cirkle.app.model.entity.Comment;
import blog.cirkle.app.model.entity.Post;
import blog.cirkle.app.model.entity.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
	Comment createComment(User currentUser, Post post, Long commentId, CreateCommentDto request);

	Page<Comment> findRootCommentsByPostId(UUID postId, Pageable pageable);

	Page<Comment> findNestedCommentsByPostId(UUID postId, Long commentId, Pageable pageable);

	Comment findByPostIdAndId(UUID postId, Long commentId);

	Comment updateComment(Comment comment, UpdateCommentDto request);

	void deleteByPostIdAndCommentId(UUID postId, Long commentId);
}
