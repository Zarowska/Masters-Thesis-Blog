package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.ai.PersonInterestEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonInterestEntityRepository extends JpaRepository<PersonInterestEntity, UUID> {
}