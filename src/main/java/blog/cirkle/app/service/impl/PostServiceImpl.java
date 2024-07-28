package blog.cirkle.app.service.impl;

import static blog.cirkle.app.utils.ModelUtils.updateMedia;

import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import blog.cirkle.app.api.rest.model.request.UpdateMediaDto;
import blog.cirkle.app.exception.NotFoundException;
import blog.cirkle.app.model.entity.Image;
import blog.cirkle.app.model.entity.Post;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.PostRepository;
import blog.cirkle.app.service.ImageService;
import blog.cirkle.app.service.PostService;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ImageService imageService;

	@Override
	public Post createPost(User currentUser, CreatePostDto request) {
		Set<Image> images = imageService.validateImages(request.getImages());

		return postRepository.save(Post.builder().author(currentUser).space(currentUser).images(images)
				.textContent(request.getText()).build());
	}

	@Override
	public Page<Post> findAllByUserSpace(UUID userId, Pageable pageable) {
		return postRepository.findBySpace_Id(userId, pageable);
	}

	@Override
	public Post findById(UUID postId) {
		return postRepository.findById(postId).orElseThrow(() -> NotFoundException.post(postId));
	}

	@Override
	public Post updatePost(Post post, UpdateMediaDto request) {
		updateMedia(imageService, post, request);
		return postRepository.save(post);
	}

	@Override
	public void deleteById(UUID id) {
		postRepository.deleteById(id);
	}

}
