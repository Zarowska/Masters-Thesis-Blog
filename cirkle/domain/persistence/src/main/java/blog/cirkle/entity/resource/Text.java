package blog.cirkle.entity.resource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "texts")
public class Text extends Resource {
	@NotNull @Column(name = "text", nullable = false, length = Integer.MAX_VALUE)
	private String text;
}