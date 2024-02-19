package com.zarowska.cirkle.api;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.utils.TestUserContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

class FileApiTest extends AbstractTest {

	TestUserContext testUserContext;
	Resource imageResource;

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
	// TODO
	// @Test
	// @Disabled
	// void shouldGetImageInfoById() throws Exception {
	// Resource imageResource = getFileFromResource("files/max_payne.png");
	// FileDto uploadedImageResponse =
	// testUserContext.getApi().images().uploadImage(imageResource);
	// Optional<FileDto> imageInfoOptional = testUserContext.getApi().images()
	// .getImageInfoById(uploadedImageResponse.getId().toString());
	// FileDto imageInfo = imageInfoOptional
	// .orElseThrow(() -> new Exception("Image info not found for ID: " +
	// uploadedImageResponse.getId()));
	//
	// assertThat(imageInfo).isNotNull();
	// }
	//
	// @Test
	// @Disabled
	// void shouldGetImageInfoList() {
	//
	// List<FileDto> listFileDto = Stream.of("max_payne.png", "blazkovic.png")
	// .map(it -> getFileFromResource("files/" + it))
	// .map(imageResource ->
	// testUserContext.getApi().images().uploadImage(imageResource)).toList();
	//
	// Optional<FilePage> filePageOptional =
	// testUserContext.getApi().images().getImageInfoList(10, 20);
	//
	// FilePage filePage = filePageOptional.get();
	//
	// List<FileDto> test = filePage.getContent();
	//
	// assertThat(listFileDto).isEqualTo(test);
	//
	// }

}
