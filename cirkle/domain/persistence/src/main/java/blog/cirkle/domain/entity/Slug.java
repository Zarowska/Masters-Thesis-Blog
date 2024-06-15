package blog.cirkle.domain.entity;

import blog.cirkle.domain.utils.SlugUtils;
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

	public <K> void update(NamedEntity<K> host) {
		if (value == null) {
			value = SlugUtils.slugifyWithSalt(host.getName());
		}
	}
}
