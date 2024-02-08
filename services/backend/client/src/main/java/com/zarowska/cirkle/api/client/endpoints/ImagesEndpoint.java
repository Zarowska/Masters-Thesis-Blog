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

	public Optional<FileDto> getImageInfoById(String imageId) {
		return null;
	}

	public FilePage getImageInfoList(Integer page, Integer size) {
		return null;
	}

	public FileDto uploadImage(Resource imageResource) {
		return doCall(() -> {
			// Create body
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("file", imageResource);

			return restTemplateWrapper.postForm(body, FileDto.class, "/user/images");
		}).get();
	}
}
