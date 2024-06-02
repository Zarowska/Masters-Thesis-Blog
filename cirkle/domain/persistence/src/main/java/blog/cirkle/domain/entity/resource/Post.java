package blog.cirkle.domain.entity.resource;

import blog.cirkle.domain.entity.NamedEntity;
import blog.cirkle.domain.entity.Slug;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends Resource implements NamedEntity {

	// todo add title

	@NotNull @ColumnDefault("false")
	@Column(name = "is_new", nullable = false)
	private Boolean isNew = false;

	@Embedded
	@NotNull @Column(name = "slug", nullable = false, length = Integer.MAX_VALUE)
	private Slug slug = new Slug();

	@Override
	protected void onCreate() {
		super.onCreate();
		slug.update(this);
	}

	@Override
	public String getName() {
		return getId().toString();
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		slug.update(this);
	}
}
