package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.FilePage;
import com.zarowska.cirkle.api.rest.ImagesApiDelegate;
import java.io.File;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImagesApiDelegateImpl implements ImagesApiDelegate {

	@Override
	public ResponseEntity<Resource> downloadImageById(UUID imageId, Integer width, Integer height) {
		return ImagesApiDelegate.super.downloadImageById(imageId, width, height);
	}

	@Override
	public ResponseEntity<File> getImageInfoById(UUID imageId) {
		return ImagesApiDelegate.super.getImageInfoById(imageId);
	}

	@Override
	public ResponseEntity<FilePage> getImageInfoList(Integer page, Integer size) {
		return ImagesApiDelegate.super.getImageInfoList(page, size);
	}

	@Override
	public ResponseEntity<Void> uploadImage(String fileName) {
		return ImagesApiDelegate.super.uploadImage(fileName);
	}
}
