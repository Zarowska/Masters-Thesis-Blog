package blog.cirkle.domain.entity.participant;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.model.newModel.RelationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "relation_requests")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
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
	@Enumerated(EnumType.STRING)
	private RelationType type;

}