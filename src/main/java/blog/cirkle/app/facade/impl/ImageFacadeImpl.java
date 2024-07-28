package blog.cirkle.app.facade.impl;

import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.facade.ImageFacade;
import blog.cirkle.app.model.entity.Image;
import blog.cirkle.app.service.ImageCache;
import blog.cirkle.app.service.ImageService;
import blog.cirkle.app.service.ModelMapperService;
import blog.cirkle.app.utils.SecurityUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageFacadeImpl implements ImageFacade {

	private final ImageCache imageCache;
	private final ImageService imageService;
	private final ModelMapperService modelMapperService;

	@Override
	public ResponseEntity<Resource> getResponseEntityWithContentById(UUID imageId) {
		Image image = imageService.findById(imageId);
		Resource imageResource = imageCache.findByIdAsResource(imageId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getMimeType())).body(imageResource);
	}

	@Override
	public Page<ImageDto> listCurrentUserImages(Pageable pageable) {
		return imageService.findAll(pageable).map(modelMapperService::toImageDto);
	}

	@Override
	public ImageDto uploadImage(MultipartFile file) {
		return modelMapperService.toImageDto(imageService.uploadImage(SecurityUtils.getCurrentUser(), file));
	}
}
