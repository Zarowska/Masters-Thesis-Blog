package blog.cirkle.app.service.impl;

import static blog.cirkle.app.utils.ModelUtils.updateMedia;

import blog.cirkle.app.api.rest.model.request.CreateCommentDto;
import blog.cirkle.app.api.rest.model.request.UpdateCommentDto;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.model.entity.Comment;
import blog.cirkle.app.model.entity.Post;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.CommentRepository;
import blog.cirkle.app.service.CommentService;
import blog.cirkle.app.service.ImageService;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final ImageService imageService;

	@Override
	public Comment createComment(User currentUser, Post post, Long commentId, CreateCommentDto request) {
		Comment parent = null;
		if (commentId != null) {
			parent = commentRepository.findById(commentId)
					.orElseThrow(() -> NotFoundException.resource("Comment", Map.of("id", commentId)));
		}
		return commentRepository.save(Comment.builder().author(currentUser).post(post).parentComment(parent)
				.images(imageService.validateImages(request.getImages())).textContent(request.getText()).build());
	}

	@Override
	public Page<Comment> findRootCommentsByPostId(UUID postId, Pageable pageable) {
		return findCommentsByPostId(postId, null, pageable);
	}

	@Override
	public Page<Comment> findNestedCommentsByPostId(UUID postId, Long commentId, Pageable pageable) {
		return findCommentsByPostId(postId, commentId, pageable);
	}

	@Override
	public Comment findByPostIdAndId(UUID postId, Long commentId) {
		return commentRepository.findByPost_IdAndId(postId, commentId)
				.orElseThrow(() -> NotFoundException.resource("Comment", Map.of("postId", postId, "id", commentId)));
	}

	@Override
	public Comment updateComment(Comment comment, UpdateCommentDto request) {
		updateMedia(imageService, comment, request);
		return commentRepository.save(comment);
	}

	@Override
	public void deleteByPostIdAndCommentId(UUID postId, Long commentId) {
		commentRepository.deleteByIdAndPost_Id(commentId, postId);
	}

	private Page<Comment> findCommentsByPostId(UUID postId, Long commentId, Pageable pageable) {
		if (commentId == null) {
			return commentRepository.findByParentCommentNullAndPost_Id(postId, pageable);
		} else {
			return commentRepository.findByPost_IdAndParentCommentNotNullAndParentComment_Id(postId, commentId,
					pageable);
		}
	}
}
