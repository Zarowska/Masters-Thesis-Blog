package blog.cirkle.domain.repository.resource;

import blog.cirkle.domain.entity.resource.Reaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, UUID> {
}