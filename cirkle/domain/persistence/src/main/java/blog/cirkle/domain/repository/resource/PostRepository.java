package blog.cirkle.domain.repository.resource;

import blog.cirkle.domain.entity.resource.Post;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, UUID> {
	Page<Post> findByAuthor_Id(UUID id, Pageable pageable);
}