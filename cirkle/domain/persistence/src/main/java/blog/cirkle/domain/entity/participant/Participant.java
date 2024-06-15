package blog.cirkle.domain.entity.participant;

import blog.cirkle.domain.entity.BaseEntity;
import blog.cirkle.domain.entity.NamedEntity;
import blog.cirkle.domain.entity.Slug;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
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

	@Embedded
	@Column(name = "slug", nullable = false)
	@Builder.Default
	private Slug slug = new Slug();

	@Override
	protected void onCreate() {
		super.onCreate();
		slug.update(this);
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		if (slug.getValue() == null) {
			slug.update(this);
		}
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