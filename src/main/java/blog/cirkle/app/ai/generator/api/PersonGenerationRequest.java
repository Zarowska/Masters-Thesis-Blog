package blog.cirkle.app.ai.generator.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonGenerationRequest {
	private String fullName;
	private Integer age;
	private String gender;
	private String nationality;
	private String origin;
	private String liveCity;
	private String liveCountry;
}
