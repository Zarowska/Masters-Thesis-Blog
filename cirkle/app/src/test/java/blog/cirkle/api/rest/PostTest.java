package blog.cirkle.api.rest;

import static blog.cirkle.api.rest.ImagesTest.SUNSET_IMAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.request.CreatePostDto;
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
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostDto().setText("simple text post"));
		});
	}

	@Test
	void shouldCreatePostWithImages() {
		asAlice(ctx -> {
			FileDto uploadedFile = ctx.files().upload(new File(SUNSET_IMAGE));
			PostDto post = ctx.posts().create(ctx.getId(),
					new CreatePostDto().setText("simple text post").setImages(List.of(uploadedFile.getId())));
			System.out.println(post);
		});
	}

	@Test
	void postShouldAppearInFollowerFeed() {
		asAlice(alice -> {
			FileDto uploadedFile = alice.files().upload(new File(SUNSET_IMAGE));
			PostDto post = alice.posts().create(alice.getId(),
					new CreatePostDto().setText("simple text post for feed").setImages(List.of(uploadedFile.getId())));

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
