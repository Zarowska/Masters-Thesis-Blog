package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.Comment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByPost_IdAndId(UUID id, Long commentId);

	long deleteByIdAndPost_Id(Long id, UUID postId);

	Page<Comment> findByParentCommentNullAndPost_Id(UUID id, Pageable pageable);

	Page<Comment> findByPost_IdAndParentCommentNotNullAndParentComment_Id(UUID id, Long id1, Pageable pageable);
}