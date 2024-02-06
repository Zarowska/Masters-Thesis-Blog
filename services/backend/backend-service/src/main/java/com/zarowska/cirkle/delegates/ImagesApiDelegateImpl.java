package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.FileDto;
import com.zarowska.cirkle.api.model.FilePage;
import com.zarowska.cirkle.api.rest.ImagesApiDelegate;
import com.zarowska.cirkle.domain.model.ImageDto;
import com.zarowska.cirkle.facade.ImagesFacade;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImagesApiDelegateImpl implements ImagesApiDelegate {

	private final ImagesFacade imagesFacade;

	@Override
	public ResponseEntity<Resource> downloadImageById(UUID imageId, Integer width, Integer height) {
		ImageDto image = imagesFacade.downloadById(imageId, width, height);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, image.getMediaType()).body(image.getResource());
	}

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return ImagesApiDelegate.super.getRequest();
	}

	@Override
	public ResponseEntity<FileDto> getImageInfoById(UUID imageId) {
		return ImagesApiDelegate.super.getImageInfoById(imageId);
	}

	@Override
	public ResponseEntity<FilePage> getImageInfoList(Integer page, Integer size) {
		return ImagesApiDelegate.super.getImageInfoList(page, size);
	}

	@Override
	public ResponseEntity<FileDto> uploadImage(MultipartFile file) {
		return ResponseEntity.ok(imagesFacade.save(file));
	}
}
