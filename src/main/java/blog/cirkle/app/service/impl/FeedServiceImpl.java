package blog.cirkle.app.service.impl;

import blog.cirkle.app.model.entity.Feed;
import blog.cirkle.app.model.entity.Post;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.FeedRepository;
import blog.cirkle.app.service.FeedService;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
	private final FeedRepository feedRepository;

	@Override
	public void sendToFeeds(Post post) {
		Set<User> followers = post.getAuthor().getFollowers();
		followers.stream().forEach(follower -> feedRepository.save(Feed.builder().user(follower).post(post).build()));

		// add algorithm to add posts to random users based on author score (number of
		// reactions, etc)
	}

	@Override
	public Page<Post> getPosts(UUID ownerId, Pageable pageable) {
		return feedRepository.findByUser_IdOrderByIdDesc(ownerId, pageable).map(Feed::getPost);
	}
}
