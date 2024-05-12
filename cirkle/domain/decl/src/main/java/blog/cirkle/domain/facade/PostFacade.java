package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.CreatePostDto;
import blog.cirkle.domain.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostFacade {
	Page findByUserId(UUID userId, Pageable pageable);

	PostDto createOne(UUID userId, CreatePostDto request);
}
