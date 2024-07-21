package blog.cirkle.domain.entity.participant;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.entity.resource.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "feed")
@NoArgsConstructor
public class Feed extends BaseEntity {

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "participant_id", nullable = false)
	private Participant participant;

	@NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	public Feed(Participant participant, Post post) {
		this.participant = participant;
		this.post = post;
	}

}