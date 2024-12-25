package blog.cirkle.app.facade;

import blog.cirkle.app.api.rest.model.ImageDto;
import java.io.File;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface ImageFacade {

	@Transactional(readOnly = true)
	ResponseEntity<Resource> getResponseEntityWithContentById(UUID imageId);

	@Transactional(readOnly = true)
	Page<ImageDto> listCurrentUserImages(Pageable pageable);

	ImageDto uploadImage(MultipartFile file);

	ImageDto uploadImage(File file);
}
