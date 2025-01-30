package blog.cirkle.app.ai.generator.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImageGenerationRequest {
	private PersonDescription author;
	private String photoType;
	private String activity;
	private String setting;
}
