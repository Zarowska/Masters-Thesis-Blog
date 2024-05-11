package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.resource.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostService {
    Page<Post> findByUserId(UUID userId, Pageable pageable);
}
