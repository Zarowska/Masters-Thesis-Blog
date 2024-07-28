package blog.cirkle.app.service;

import blog.cirkle.app.model.entity.Post;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedService {
	void sendToFeeds(Post post);

	Page<Post> getPosts(UUID ownerId, Pageable pageable);
}
