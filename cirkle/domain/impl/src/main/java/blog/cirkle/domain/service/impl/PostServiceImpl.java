package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.participant.Feed;
import blog.cirkle.domain.entity.participant.Participant;
import blog.cirkle.domain.entity.resource.Post;
import blog.cirkle.domain.exception.ResourceNotFoundException;
import blog.cirkle.domain.repository.participant.FeedRepository;
import blog.cirkle.domain.repository.participant.UserRepository;
import blog.cirkle.domain.repository.resource.PostRepository;
import blog.cirkle.domain.repository.resource.ResourceRepository;
import blog.cirkle.domain.service.PostService;
import blog.cirkle.domain.service.ResourceService;
import java.util.*;
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
	private final FeedRepository feedRepository;
	private final ResourceRepository resourceRepository;
	private final UserRepository userRepository;
	private final ResourceService resourceService;

	@Override
	@Transactional(readOnly = true)
	public Page<Post> listByUserId(UUID userId, Pageable pageable) {
		return postRepository.findByAuthor_Id(userId, pageable);
	}

	@Transactional
	@Override
	public Post save(Post post) {
		return resourceService.save(post);
	}

	@Override
	public Page<Post> getFeedByUserId(UUID id, Pageable pageable) {
		Page<Feed> byParticipantId = feedRepository.findByParticipant_Id(id, pageable);
		return byParticipantId.map(Feed::getPost);
	}

	@Override
	public void addToFeed(Participant target, UUID postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", Map.of("id", postId)));
		feedRepository.save(new Feed(target, post));
	}

	@Override
	public Optional<Post> findByUserId(UUID userId, UUID postId) {
		return postRepository.findByAuthor_IdAndId(userId, postId);
	}

	@Override
	public void deletePostById(UUID userId, UUID postId) {
		postRepository.deleteByAuthor_IdAndId(userId, postId);
	}
}
