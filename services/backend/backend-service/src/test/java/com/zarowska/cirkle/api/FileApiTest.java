package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.api.model.FilePage;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.utils.TestUserContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

class FileApiTest extends AbstractTest {

	TestUserContext testUserContext;

	@BeforeEach
	void setUp() {
		testUserContext = context("Bob Marley", "bob@marley.com", "http:/some/avatar");
		testUserContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@BeforeAll
	static void clearCache() {
	}

	@Test
	void shouldUploadFile() {
		Resource imageResource = getFileFromResource("files/max_payne.png");
		FileDto response = testUserContext.getApi().images().uploadImage(imageResource);
		assertThat(response.getMediaType()).isEqualTo("image/png");
	}

	@Test
	void shouldDownloadFile() throws Exception {
		Resource imageResource = getFileFromResource("files/max_payne.png");
		FileDto response = testUserContext.getApi().images().uploadImage(imageResource);
		Resource resource = testUserContext.getApi().images().downloadImageById(response.getId(), null, null);

		assertThat(resource.contentLength()).isEqualTo(imageResource.contentLength());
	}

	@Test
	void shouldGetImageInfoById() throws Exception {
		Resource imageResource = getFileFromResource("files/max_payne.png");
		FileDto uploadedImageResponse = testUserContext.getApi().images().uploadImage(imageResource);
		Optional<FileDto> imageInfoOptional = testUserContext.getApi().images()
				.getImageInfoById(uploadedImageResponse.getId());
		FileDto imageInfo = imageInfoOptional
				.orElseThrow(() -> new Exception("Image info not found for ID: " + uploadedImageResponse.getId()));

		assertThat(imageInfo).isNotNull();
	}

	@Test
	void shouldGetImageInfoList() {
		List<FileDto> expectedList = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> testUserContext.getApi().images().uploadImage(imageResource)).toList();

		Optional<FilePage> filePageOptional = testUserContext.getApi().images().getImageInfoList(0, 20);

		FilePage filePage = filePageOptional.get();

		List<FileDto> actualList = filePage.getContent();

		assertThat(actualList).containsAll(expectedList);
	}

	@Test
	void deleteImageTest() {
		Resource imageResource = getFileFromResource("files/max_payne.png");
		FileDto image = testUserContext.getApi().images().uploadImage(imageResource);
		Post post = testUserContext.getApi().posts().createPost(testUserContext.getUserId(),
				CreatePostRequest.builder().text("Sample post").images(List.of(image.getUrl())).build()).get();
		// delete image
		Post beforeDeletePost = testUserContext.getApi().posts()
				.getUserPostByPostId(testUserContext.getUserId(), post.getId()).get();
		assertThat(beforeDeletePost.getImages()).isNotEmpty();

		testUserContext.getApi().images().deleteImage(image.getId());

		// post related to image should not have image anymore
		Post afterDeletePost = testUserContext.getApi().posts()
				.getUserPostByPostId(testUserContext.getUserId(), post.getId()).get();
		assertThat(afterDeletePost.getImages()).isEmpty();

	}
}
