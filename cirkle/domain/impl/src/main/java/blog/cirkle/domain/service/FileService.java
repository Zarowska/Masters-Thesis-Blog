package blog.cirkle.domain.service;

import blog.cirkle.domain.entity.resource.ImageFile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	ImageFile create(MultipartFile file);

	Optional<ImageFile> findById(UUID id);
}
