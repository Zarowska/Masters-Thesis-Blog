package blog.cirkle.app.repository;

import blog.cirkle.app.model.entity.ParticipantRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRequestRepository extends JpaRepository<ParticipantRequest, UUID> {
	Optional<ParticipantRequest> findBySender_IdAndReceiver_IdAndType(UUID id, UUID id1,
			ParticipantRequest.ParticipantRequestType type);

	Page<ParticipantRequest> findByReceiver_Id(UUID id, Pageable pageable);

	Optional<ParticipantRequest> findByIdAndReceiver_Id(UUID id, UUID id1);

	long deleteByReceiver_IdAndId(UUID id, UUID id1);
}