package blog.cirkle.domain.repository.resource;

import blog.cirkle.domain.entity.resource.Comment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
	List<Comment> findByParentNotNullAndParent_Id(UUID id);

	Page<Comment> findByPost_Id(UUID id, Pageable pageable);

	Page<Comment> findByPost_IdAndParentNull(UUID id, Pageable pageable);
}