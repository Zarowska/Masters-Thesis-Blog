package blog.cirkle.domain.repository.participant;

import blog.cirkle.domain.entity.participant.Feed;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, UUID> {
	Page<Feed> findByPost_Author_Id(UUID id, Pageable pageable);

	Page<Feed> findByParticipant_Id(UUID id, Pageable pageable);
}