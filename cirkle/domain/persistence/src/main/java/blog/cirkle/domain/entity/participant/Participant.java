package blog.cirkle.domain.entity.participant;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.entity.NamedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "participants")
@NoArgsConstructor
@SuperBuilder
public abstract class Participant extends BaseEntity implements NamedEntity<UUID> {

	@NotNull @Column(name = "avatar_url", nullable = false, length = Integer.MAX_VALUE)
	private String avatarUrl;

	@Column(name = "slug", nullable = false)
	private String slug = null;

	@Override
	protected void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
	}

	public User asUser() {
		if (this instanceof User user) {
			return user;
		}
		throw new ClassCastException("Can't cast " + this.getClass().getSimpleName() + " to User");
	}

	public abstract ParticipantType getType();

	public enum ParticipantType {
		USER, GROUP
	}
}