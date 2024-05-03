package blog.cirkle.domain.repository.resource;

import blog.cirkle.domain.entity.resource.ImageFile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, UUID> {
}