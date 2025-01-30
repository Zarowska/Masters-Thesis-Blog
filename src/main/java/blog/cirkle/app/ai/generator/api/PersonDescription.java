package blog.cirkle.app.ai.generator.api;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonDescription {
	private String fullName;
	private Integer age;
	private String gender;
	private String nationality;
	private String origin;
	private String liveCity;
	private String liveCountry;
	private String email;
	private String bio;
	private String appearance;
	@Builder.Default
	private List<PersonInterest> interests = new ArrayList<>();

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Data
	public static class PersonInterest {
		private String title;
		private String description;
	}
}
