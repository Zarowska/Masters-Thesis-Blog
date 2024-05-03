package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.CreatePostDto;
import blog.cirkle.domain.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostFacade {
	Page findByUserId(int userId, Pageable pageable);

	PostDto createOne(int userId, CreatePostDto request);
}
