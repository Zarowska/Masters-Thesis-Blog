package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.resource.Post;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
	Page<Post> findByUserId(UUID userId, Pageable pageable);

	Post save(Post post);
}
