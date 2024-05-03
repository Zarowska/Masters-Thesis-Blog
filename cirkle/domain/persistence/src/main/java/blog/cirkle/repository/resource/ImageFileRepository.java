package blog.cirkle.repository.resource;

import blog.cirkle.entity.resource.ImageFile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, UUID> {
}