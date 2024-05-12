package blog.cirkle.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostGenerationResponse {
	private String content;
	private String imgPrompt;
}
