package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.resource.Comment;
import blog.cirkle.domain.repository.resource.CommentRepository;
import blog.cirkle.domain.service.CommentService;
import blog.cirkle.domain.service.ResourceService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final ResourceService resourceService;

	@Override
	public Comment save(Comment comment) {
		return resourceService.save(comment);
	}

	@Override
	public List<Comment> findByParent(Comment comment) {
		return commentRepository.findByParentNotNullAndParent_Id(comment.getId());
	}

	@Override
	public Page<Comment> findByPostId(UUID userID, UUID postId, Pageable pageable) {
		return commentRepository.findByPost_IdAndParentNull(postId, pageable);
	}

	@Override
	public void deleteById(UUID commentId) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		comment.ifPresent(c -> {
			commentRepository.deleteById(c.getId());
		});
	}

	@Override
	public Optional<Comment> findByPostIdAndId(UUID postId, UUID commentId) {
		return commentRepository.findById(commentId);
	}

	@Override
	public Optional<Comment> findById(UUID userId, UUID postId, UUID commentId) {
		return commentRepository.findById(commentId);
	}
}
