package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.CreatePostDto;
import blog.cirkle.domain.model.PostDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostFacade {
	Page<PostDto> findByUserId(UUID userId, Pageable pageable);

	PostDto createOne(UUID userId, CreatePostDto request);
}
