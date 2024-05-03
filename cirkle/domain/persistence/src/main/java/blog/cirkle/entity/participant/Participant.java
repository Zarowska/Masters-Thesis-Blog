package blog.cirkle.entity.participant;

import blog.cirkle.entity.BaseEntity;
import blog.cirkle.entity.NamedEntity;
import blog.cirkle.entity.Slug;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "participants")
@NoArgsConstructor
@SuperBuilder
public abstract class Participant extends BaseEntity implements NamedEntity {

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
		slug.update(this);
	}
}