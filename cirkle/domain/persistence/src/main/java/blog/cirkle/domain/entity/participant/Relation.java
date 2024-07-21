package blog.cirkle.domain.entity.participant;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.model.response.RelationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "relations")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Relation extends BaseEntity {

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "owner_id", nullable = false)
	private Participant owner;

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "related_id", nullable = false)
	private Participant related;

	@NotNull @Column(name = "type", nullable = false, length = Integer.MAX_VALUE)
	@Enumerated(EnumType.STRING)
	private RelationType type;

	// todo add messages
}