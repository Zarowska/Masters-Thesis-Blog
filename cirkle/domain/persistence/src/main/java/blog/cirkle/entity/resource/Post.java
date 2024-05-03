package blog.cirkle.entity.resource;

import jakarta.persistence.Column;
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
public class Post extends Resource {

	// todo add title

	@NotNull @ColumnDefault("false")
	@Column(name = "is_new", nullable = false)
	private Boolean isNew = false;

	@NotNull @Column(name = "slug", nullable = false, length = Integer.MAX_VALUE)
	private String slug;

}