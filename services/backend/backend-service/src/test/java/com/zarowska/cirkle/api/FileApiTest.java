package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.FileDto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

class FileApiTest extends AbstractTest {

	@BeforeAll
	static void clearCache() {

	}

	@Test
	@Disabled
	void shouldUploadFile() throws Exception {
		context("test", "test@test.com", "").apply(ctx -> {
			Resource imageResource = getFileFromResource("files/max_payne.png");
			FileDto response = ctx.getApi().images().uploadImage(imageResource);
			assertThat(response.getMediaType()).isEqualTo("image/png");
		});
	}

	@Test
	@Disabled
	void shouldDownloadFile() throws Exception {
		context("test", "test@test.com", "").apply(ctx -> {
			Resource imageResource = getFileFromResource("files/max_payne.png");
			FileDto response = ctx.getApi().images().uploadImage(imageResource);

			Resource resource = ctx.getApi().images().downloadImageById(response.getId(), null, null);

			assertThat(resource.contentLength()).isEqualTo(imageResource.contentLength());
		});
	}

	public Resource getFileFromResource(String filename) throws IOException {
		return new ByteArrayResource(getFileFromResourcesAsByteArray(filename)) {
			@Override
			public String getFilename() {
				int slashIndex = filename.lastIndexOf('/');
				return filename.substring(slashIndex + 1);
			}
		};
	}

	public byte[] getFileFromResourcesAsByteArray(String filename) throws IOException {
		Path path = Paths.get(getClass().getClassLoader().getResource(filename).getPath());
		return Files.readAllBytes(path);
	}
}
