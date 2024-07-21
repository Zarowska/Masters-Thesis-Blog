package blog.cirkle.api.rest;

import static blog.cirkle.api.rest.ImagesTest.SUNSET_IMAGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.api.rest.client.exception.ClientResponseException;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.request.CreatePostRequest;
import blog.cirkle.domain.model.response.FileDto;
import blog.cirkle.domain.model.response.PostDto;
import java.io.File;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PostTest extends AbstractApiTest {

	@Test
	void shouldCreateTextPost() {
		asAlice(ctx -> {
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post"));
		});
	}

	@Test
	void shouldCreatePostWithImages() {
		asAlice(ctx -> {
			FileDto uploadedFile = ctx.files().upload(new File(SUNSET_IMAGE));
			PostDto post = ctx.posts().create(ctx.getId(),
					new CreatePostRequest().setText("simple text post").setImages(List.of(uploadedFile.getId())));
			System.out.println(post);
		});
	}

	@Test
	void shouldFindPostById() {
		asAlice(ctx -> {
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post"));

			PostDto found = ctx.posts().findByUserId(ctx.getId(), post.getId());

			assertThat(found).isEqualTo(post);

			asBob(bobCtx -> {
				PostDto foundByBob = bobCtx.posts().findByUserId(ctx.getId(), post.getId());

				assertThat(foundByBob).isEqualTo(post);
			});

		});
	}

	@Test
	void shouldListPostsByUserId() {
		asAlice(ctx -> {
			List<PostDto> posts = List.of(
					ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post1")),
					ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post2")),
					ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post3")));

			PaginatedResponse<PostDto> found = ctx.posts().listByUserId(ctx.getId());

			assertThat(found.getContent()).isEqualTo(posts);

			asBob(bobCtx -> {
				PaginatedResponse<PostDto> foundByBob = bobCtx.posts().listByUserId(ctx.getId());

				assertThat(foundByBob.getContent()).isEqualTo(posts);
			});
		});
	}

	@Test
	void shouldDeleteOwnPost() {
		asAlice(ctx -> {
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post"));
			ctx.posts().deleteById(ctx.getId(), post.getId());

			assertThatThrownBy(() -> ctx.posts().findByUserId(ctx.getId(), post.getId()))
					.isInstanceOf(ClientResponseException.class)
					.hasMessageContaining("Resource of type Post with postId=").hasMessageContaining("not found");
		});
	}

	@Test
	void shouldNotDeleteOthersPost() {
		asAlice(ctx -> {
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post"));
			asBob(bobCtx -> {
				assertThatThrownBy(() -> bobCtx.posts().deleteById(ctx.getId(), post.getId()))
						.isInstanceOf(ClientResponseException.class)
						.hasMessageContaining("Only author can delete this post");
			});
		});
	}

	@Test
	void postShouldAppearInFollowerFeed() {
		asAlice(alice -> {
			FileDto uploadedFile = alice.files().upload(new File(SUNSET_IMAGE));
			PostDto post = alice.posts().create(alice.getId(), new CreatePostRequest()
					.setText("simple text post for feed").setImages(List.of(uploadedFile.getId())));

			// bob follows alice
			asBob(bob -> {
				PaginatedResponse<PostDto> feed = bob.user().feed(0, 100);
				Optional<PostDto> postInFeed = feed.getContent().stream()
						.filter(it -> it.getAuthor().getId().equals(alice.getId())).findFirst();
				assertThat(postInFeed.isPresent()).isTrue();
				assertThat(postInFeed.get().getText()).isEqualTo("simple text post for feed");
			});
		});

	}
}
