package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.entity.resource.Post;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.repository.participant.UserRepository;
import blog.cirkle.domain.repository.resource.PostRepository;
import blog.cirkle.domain.repository.resource.ResourceRepository;
import blog.cirkle.domain.security.UserContextHolder;
import blog.cirkle.domain.service.PostService;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ResourceRepository resourceRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<Post> findByUserId(UUID userId, Pageable pageable) {
		return postRepository.findByAuthor_Id(userId, pageable);
	}

	@Transactional
	@Override
	public Post save(Post post) {
		UUID userId = UserContextHolder.getCurrentUser().get().getId();
		User currentUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));
		post.setAuthor(currentUser);
		post.getContent().forEach(it -> it.setAuthor(currentUser));
		resourceRepository.saveAll(post.getContent());
		return postRepository.save(post);
	}
}
