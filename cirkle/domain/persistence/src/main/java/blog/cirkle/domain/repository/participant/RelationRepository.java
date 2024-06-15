package blog.cirkle.domain.repository.participant;

import blog.cirkle.domain.entity.participant.Participant;
import blog.cirkle.domain.entity.participant.Relation;
import blog.cirkle.domain.model.newModel.RelationType;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, UUID> {
	Page<Relation> findByOwner_IdAndTypeIn(UUID ownerId, Collection<RelationType> types, Pageable pageable);

	Optional<Relation> findByOwnerAndRelatedAndType(Participant owner, Participant related, RelationType type);

	long deleteByTypeAndOwnerInAndRelatedIn(RelationType type, Collection<Participant> owners,
			Collection<Participant> relateds);
}