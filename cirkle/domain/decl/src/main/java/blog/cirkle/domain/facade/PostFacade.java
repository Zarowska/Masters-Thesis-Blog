package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.request.CreatePostDto;
import blog.cirkle.domain.model.response.PostDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface PostFacade {
	@Transactional(readOnly = true)
	Page<PostDto> findByUserId(UUID userId, Pageable pageable);

	@Transactional
	PostDto createOne(UUID userId, CreatePostDto request);

	@Transactional(readOnly = true)
	Page<PostDto> feed(Pageable pageable);
}
