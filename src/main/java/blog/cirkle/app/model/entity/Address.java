package blog.cirkle.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

	@Column(name = "country")
	private String country;

	@Column(name = "city")
	private String city;

	@Column(name = "hometown")
	private String hometown;

}