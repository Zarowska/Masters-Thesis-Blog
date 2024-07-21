package blog.cirkle.domain.entity.participant;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "groups")
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("GROUP")
public class Group extends Participant {
	@NotNull @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
	@Builder.Default
	private String title = "New group";

	@Override
	public String getName() {
		return title;
	}

	@Override
	public ParticipantType getType() {
		return ParticipantType.GROUP;
	}
}