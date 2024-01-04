package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.File;
import com.zarowska.cirkle.api.model.FilePage;
import java.util.Optional;
import org.springframework.core.io.Resource;

public class ImagesEndpoint extends AbstractClientEndpoint {

	public ImagesEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public Resource downloadImageById(String imageId, Integer widht, Integer heigth) {
		return null;
	}

	public Optional<File> getImageInfoById(String imageId) {
		return null;
	}

	public FilePage getImageInfoList(Integer page, Integer size) {
		return null;
	}

	public Void uploadImage(String fileName) {
		return null;
	}
}
