package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.Post;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, UUID> {
	Page<Post> findBySpace_Id(UUID id, Pageable pageable);
}