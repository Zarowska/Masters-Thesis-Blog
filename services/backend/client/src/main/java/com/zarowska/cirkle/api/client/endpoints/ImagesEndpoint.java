package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.api.model.FilePage;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ImagesEndpoint extends AbstractClientEndpoint {

	public ImagesEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Resource downloadImageById(UUID imageId, Integer widht, Integer heigth) {
		return doCall(() -> restTemplateWrapper.get(Resource.class, "/images/{imageId}", imageId)).get();
	}

	// public Optional<FileDto> getImageInfoById(String imageId) {
	// return Optional
	// .of(doCall(() -> restTemplateWrapper.get(FileDto.class,
	// "/user/images/{imageId}", imageId)).get());
	// }
	//
	// public Optional<FilePage> getImageInfoList(Integer page, Integer size) {
	// return Optional.of(doCall(() -> restTemplateWrapper.get(FilePage.class,
	// "/user/images/", page, size)).get());
	// }

	public FileDto uploadImage(Resource imageResource) {
		return doCall(() -> {
			// Create body
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("file", imageResource);

			return restTemplateWrapper.postForm(body, FileDto.class, "/user/images");
		}).get();
	}

	public Optional<FileDto> getImageInfoById(UUID imageId) {
		return doCall(() -> restTemplateWrapper.get(FileDto.class, "/user/images/{imageId}", imageId));
	}

	public Optional<FilePage> getImageInfoList(int page, int size) {
		return doCall(
				() -> restTemplateWrapper.get(FilePage.class, "/user/images?page=%d&size=%d".formatted(page, size)));
	}

	public void deleteImage(UUID imageId) {
		doCall(() -> restTemplateWrapper.delete(String.class, "/user/images/{imageId}", imageId));
	}
}
