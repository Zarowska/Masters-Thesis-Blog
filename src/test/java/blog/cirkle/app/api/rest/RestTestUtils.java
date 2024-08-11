package blog.cirkle.app.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestTestUtils {
	public static PostDto twoBunniesPost(ApiClient client) {
		List<ImageDto> images = bunnyImages(client);
		PostDto post = client.posts.createPost(CreatePostDto.builder().text("Post about two bunnies")
				.images(images.stream().map(ImageDto::getId).toList()).build());
		return post;
	}

	public static @NotNull List<ImageDto> bunnyImages(ApiClient client) {
		return List.of(client.user.uploadImage(new ClassPathResource("/images/bunny.jpg")),
				client.user.uploadImage(new ClassPathResource("/images/bunny.png")));
	}

	public static void assertPostVisibility(ApiClient client, PostDto post) {
		// should be visible in my posts

		PaginatedResponse<PostDto> userPosts = client.user.listUserPosts(Pageable.DEFAULT);
		assertTrue(userPosts.getContent().stream().filter(it -> it.equals(post)).findFirst().isPresent());

		// should be visible in users/posts

		PaginatedResponse<PostDto> userPosts2 = client.users.listPostsByUserId(client.getUserId(), Pageable.DEFAULT);
		assertTrue(userPosts2.getContent().stream().filter(it -> it.equals(post)).findFirst().isPresent());

		// should be visible in /posts

		PostDto post2 = client.posts.getPost(post.getId());
		assertEquals(post, post2);
	}
}
