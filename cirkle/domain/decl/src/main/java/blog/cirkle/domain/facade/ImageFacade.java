package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.FileWrapper;
import blog.cirkle.domain.model.response.FileDto;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFacade {
	@Transactional
	FileDto uploadFile(MultipartFile file);

	@Transactional(readOnly = true)
	FileWrapper download(UUID id, Integer width, Integer height);
}
