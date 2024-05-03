package blog.cirkle.entity.resource;

import blog.cirkle.entity.BaseEntity;
import blog.cirkle.entity.participant.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "reactions")
public class Reaction extends BaseEntity {

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource resource;

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@NotNull @Column(name = "value", nullable = false)
	private Integer value;

}