package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.FileWrapper;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFacade {
	@Transactional
	UUID uploadFile(MultipartFile file);

	@Transactional(readOnly = true)
	FileWrapper download(UUID id, Integer width, Integer height);
}
