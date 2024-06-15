package blog.cirkle.domain.repository.participant;

import blog.cirkle.domain.entity.participant.RelationRequest;
import blog.cirkle.domain.model.newModel.RelationType;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRequestRepository extends JpaRepository<RelationRequest, UUID> {
	boolean existsByInitiator_IdAndTarget_IdAndType(UUID initiatorId, UUID targetId, RelationType type);

	Page<RelationRequest> findByTarget_IdAndTypeIn(UUID id, Collection<RelationType> types, Pageable pageable);
}