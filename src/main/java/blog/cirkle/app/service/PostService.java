package blog.cirkle.app.service;

import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import blog.cirkle.app.api.rest.model.request.UpdateMediaDto;
import blog.cirkle.app.model.entity.Post;
import blog.cirkle.app.model.entity.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
	Post createPost(User currentUser, CreatePostDto request);

	Page<Post> findAllByUserSpace(UUID userId, Pageable pageable);

	Post findById(UUID postId);

	Post updatePost(Post post, UpdateMediaDto request);

	void deleteById(UUID id);
}
