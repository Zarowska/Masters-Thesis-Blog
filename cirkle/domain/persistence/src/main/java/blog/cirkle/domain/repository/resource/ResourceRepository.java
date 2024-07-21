package blog.cirkle.domain.repository.resource;

import blog.cirkle.domain.entity.resource.Resource;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, UUID> {
}