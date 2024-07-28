package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.Participant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}