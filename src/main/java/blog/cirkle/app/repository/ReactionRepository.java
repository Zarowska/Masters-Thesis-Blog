package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}