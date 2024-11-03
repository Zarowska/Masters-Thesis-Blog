package blog.cirkle.app.service.impl;

import blog.cirkle.app.model.entity.Feed;
import blog.cirkle.app.model.entity.Post;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.FeedRepository;
import blog.cirkle.app.service.FeedService;
import blog.cirkle.app.service.UserService;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
	private final FeedRepository feedRepository;
	private final UserService userService;
	private final Random random = new Random();

	@Override
	public void sendToFeeds(Post post) {
		Set<User> followers = post.getAuthor().getFollowers();
		followers.stream().forEach(follower -> addToFeed(post, follower));

		int usersToShare = (int) (userService.totalUsers() * 0.10);

		if (usersToShare > followers.size()) {
			for (int i = 0; i < usersToShare; i++) {
				Page<User> randomPage = userService.findAll("", PageRequest.of(random.nextInt(usersToShare), 1));
				if (randomPage.hasContent()) {
					User randomUser = randomPage.getContent().get(0);
					if (!randomUser.getId().equals(post.getAuthor().getId()) && !followers.contains(randomUser)) {
						addToFeed(post, randomUser);
					}
				}
			}
		}
	}

	private Feed addToFeed(Post post, User user) {
		return feedRepository.save(Feed.builder().user(user).post(post).build());
	}

	@Override
	public Page<Post> getPosts(UUID ownerId, Pageable pageable) {
		return feedRepository.findByUser_IdOrderByIdDesc(ownerId, pageable).map(Feed::getPost);
	}
}
