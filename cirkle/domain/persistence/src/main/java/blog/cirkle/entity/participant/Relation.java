package blog.cirkle.entity.participant;

import blog.cirkle.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "relations")
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
	private String type;

	// FRIEND
	// FOLLOWER
	// GROUP_MEMBER
	// GROUP_ADMIN
	// GROUP_OWNER

	// todo add messages
}