package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.resource.Comment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
	Comment save(Comment comment);

	List<Comment> findByParent(Comment comment);

	Page<Comment> findByPostId(UUID userID, UUID postId, Pageable pageable);

	void deleteById(UUID commentId);

	Optional<Comment> findByPostIdAndId(UUID postId, UUID commentId);

	Optional<Comment> findById(UUID userId, UUID postId, UUID commentId);

}
