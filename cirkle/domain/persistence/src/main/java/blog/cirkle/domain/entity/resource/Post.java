package blog.cirkle.domain.entity.resource;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends Resource {

	@NotNull @ColumnDefault("false")
	@Column(name = "is_new", nullable = false)
	private Boolean isNew = false;

}
