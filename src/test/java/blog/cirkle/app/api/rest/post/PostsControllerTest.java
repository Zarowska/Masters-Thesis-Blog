package blog.cirkle.app.api.rest.post;

import static blog.cirkle.app.api.rest.RestTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import blog.cirkle.app.api.rest.AbstractApiTest;
import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.request.CreateCommentDto;
import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import blog.cirkle.app.api.rest.model.request.UpdateMediaDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class PostsControllerTest extends AbstractApiTest {

	@Test
	void createPost() {
		asEve(eve -> {
			PostDto post = eve.posts.createPost(CreatePostDto.builder().text("Simple post").build());
			assertEquals("Simple post", post.getText());

			assertPostVisibility(eve, post);

		});
	}

	@Test
	void createPostWithImages() {
		asEve(eve -> {
			List<ImageDto> images = bunnyImages(eve);
			PostDto post = eve.posts.createPost(CreatePostDto.builder().text("Post about two bunnies")
					.images(images.stream().map(ImageDto::getId).toList()).build());

			assertEquals("Post about two bunnies", post.getText());
		});
	}

	@Test
	void updatePostByPostId() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);

			List<ImageDto> newImages = new ArrayList<>();
			newImages.addAll(post.getImages());
			newImages.add(eve.user.uploadImage(new ClassPathResource("/images/bunny-8.png")));

			PostDto updatedPost = eve.posts.updatePost(post.getId(), UpdateMediaDto.builder()
					.images(newImages.stream().map(ImageDto::getId).toList()).text("Post about three bunnies").build());

			assertEquals("Post about three bunnies", updatedPost.getText());
			assertEquals(3, updatedPost.getImages().size());

			assertPostVisibility(eve, updatedPost);

			asAlice(alice -> {
				// other users cant update post
				assertStatus(403,
						() -> alice.posts.updatePost(post.getId(),
								UpdateMediaDto.builder().images(newImages.stream().map(ImageDto::getId).toList())
										.text("Post about three bunnies").build()));
			});

		});
	}

	@Test
	void getPostByPostId() {
		asEve(eve -> {
			PostDto post = eve.posts.createPost(CreatePostDto.builder().text("Simple post").build());
			assertEquals("Simple post", post.getText());

			PostDto actual = eve.posts.getPost(post.getId());
			assertEquals(post, actual);

			assertPostVisibility(eve, actual);
		});
	}

	@Test
	void deletePostByPostId() {
		asEve(eve -> {
			PostDto post = eve.posts.createPost(CreatePostDto.builder().text("Simple post").build());
			assertEquals("Simple post", post.getText());

			// other users cant delete post
			asAlice(alice -> {
				assertStatus(403, () -> alice.posts.deletePost(post.getId()));
			});

			eve.posts.deletePost(post.getId());

			assertStatus(404, () -> eve.posts.getPost(post.getId()));
		});
	}

	@Test
	void deletePostByPostIdWithImagesAndComments() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);

			List<ImageDto> images = bunnyImages(eve);
			CommentDto firstComment = eve.posts.createCommentOnPost(post.getId(), CreateCommentDto.builder()
					.text("First comment").images(images.stream().map(ImageDto::getId).toList()).build());

			eve.posts.deletePost(post.getId());

			assertStatus(404, () -> eve.posts.getPost(post.getId()));
		});
	}

}