package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.Feed;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	Page<Feed> findByUser_IdOrderByIdDesc(UUID id, Pageable pageable);
}