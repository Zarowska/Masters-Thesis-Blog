package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.participant.Participant;
import blog.cirkle.domain.entity.resource.Post;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
	Page<Post> listByUserId(UUID userId, Pageable pageable);

	Post save(Post post);

	Page<Post> getFeedByUserId(UUID id, Pageable pageable);

	void addToFeed(Participant target, UUID postId);

	Optional<Post> findByUserId(UUID userId, UUID postId);

	void deletePostById(UUID userId, UUID postId);
}
