package blog.cirkle.domain.facade.impl;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.entity.participant.Relation;
import blog.cirkle.domain.entity.resource.Image;
import blog.cirkle.domain.entity.resource.Post;
import blog.cirkle.domain.entity.resource.Text;
import blog.cirkle.domain.facade.PostFacade;
import blog.cirkle.domain.facade.mappers.UserMapper;
import blog.cirkle.domain.model.request.CreatePostDto;
import blog.cirkle.domain.model.response.PostDto;
import blog.cirkle.domain.model.response.RelationType;
import blog.cirkle.domain.service.FileService;
import blog.cirkle.domain.service.PostService;
import blog.cirkle.domain.service.RelationService;
import blog.cirkle.domain.service.impl.UserServiceHolder;
import java.util.*;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {

	private final PostService postService;
	private final FileService fileService;
	private final RelationService relationService;
	private final UserMapper userMapper;

	@Override
	public Page<PostDto> findByUserId(UUID userId, Pageable pageable) {
		Page<Post> posts = postService.findByUserId(userId, pageable);

		return posts.map(this::toDto);

	}

	@Override
	public PostDto createOne(UUID userId, CreatePostDto request) {
		Post post = new Post();
		post.getContent().add(new Text(request.getText()));
		List<Image> imageList = request.getImages().stream().map(fileService::findById).filter(Optional::isPresent)
				.map(Optional::get).map(Image::new).toList();
		post.getContent().addAll(imageList);
		final Post savedPost = postService.save(post);

		List<Relation> relations;
		int page = 0;
		do {
			relations = relationService
					.findRelationsByType(userId, Set.of(RelationType.FOLLOWER), Pageable.ofSize(100).withPage(page))
					.getContent();
			relations.forEach(rel -> postService.addToFeed(rel.getRelated(), savedPost.getId()));
			page++;
		} while (!relations.isEmpty());

		return toDto(savedPost);
	}

	@Override
	public Page<PostDto> feed(Pageable pageable) {
		Page<Post> feed = postService.getFeedByUserId(UserServiceHolder.currentUserOrNull().getId(), pageable);

		return feed.map(it -> toDto(it));
	}

	PostDto toDto(Post post) {
		return new PostDto().setId(post.getId()).setSlug("").setCreatedAt(post.getCreatedAt().getEpochSecond())
				.setUpdatedAt(post.getUpdatedAt().getEpochSecond()).setAuthor(userMapper.toUserDto(post.getAuthor()))
				.setText(getTextPart(post)).setComments(Collections.emptyList()).setReactions(Collections.emptyList())
				.setImages(getImages(post));

	}

	private List<UUID> getImages(Post post) {
		return filterContent(Image.class, post.getContent()).map(BaseEntity::getId).toList();
	}

	private String getTextPart(Post post) {
		return filterContent(Text.class, post.getContent()).findFirst().map(Text::getText).orElse("");

	}

	private <T> Stream<T> filterContent(Class<T> clazz, Collection objects) {
		return objects.stream().filter(it -> it.getClass().isAssignableFrom(clazz)).map(it -> (T) it);
	}

}
