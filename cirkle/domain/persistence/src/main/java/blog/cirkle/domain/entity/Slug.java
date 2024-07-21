package blog.cirkle.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Slug {
	@NotNull @Column(name = "slug", nullable = false, length = Integer.MAX_VALUE)
	private String value;

}
