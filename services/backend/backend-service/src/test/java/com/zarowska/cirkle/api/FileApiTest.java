package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.FileDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

class FileApiTest extends AbstractTest {

	@BeforeAll
	static void clearCache() {

	}

	@Test
	void shouldUploadFile() throws Exception {
		context("test", "test@test.com", "").apply(ctx -> {
			Resource imageResource = getFileFromResource("files/max_payne.png");
			FileDto response = ctx.getApi().images().uploadImage(imageResource);
			assertThat(response.getMediaType()).isEqualTo("image/png");
		});
	}

	@Test
	void shouldDownloadFile() throws Exception {
		context("test", "test@test.com", "").apply(ctx -> {
			Resource imageResource = getFileFromResource("files/max_payne.png");
			FileDto response = ctx.getApi().images().uploadImage(imageResource);

			Resource resource = ctx.getApi().images().downloadImageById(response.getId(), null, null);

			assertThat(resource.contentLength()).isEqualTo(imageResource.contentLength());
		});
	}
}
