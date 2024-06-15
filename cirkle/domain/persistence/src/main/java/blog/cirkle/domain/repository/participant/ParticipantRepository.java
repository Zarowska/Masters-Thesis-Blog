package blog.cirkle.domain.repository.participant;

import blog.cirkle.domain.entity.participant.Participant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

}