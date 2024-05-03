package blog.cirkle.domain.entity.participant;

import blog.cirkle.domain.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "relation_requests")
public class RelationRequest extends BaseEntity {

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "initiator_id", nullable = false)
	private Participant initiator;

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "target_id", nullable = false)
	private Participant target;

	@NotNull @Column(name = "type", nullable = false, length = Integer.MAX_VALUE)
	private String type;

}