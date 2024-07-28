package blog.cirkle.app.service;

import blog.cirkle.app.model.entity.Image;
import blog.cirkle.app.model.entity.Participant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	Image uploadImage(Participant owner, Resource resource);

	Image uploadImage(Participant owner, MultipartFile file);

	Image findById(UUID imageId);

	Page<Image> findAll(Pageable pageable);

	Set<Image> validateImages(Collection<UUID> images);
}
