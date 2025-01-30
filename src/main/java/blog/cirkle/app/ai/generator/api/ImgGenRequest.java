package blog.cirkle.app.ai.generator.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgGenRequest {
	@Builder.Default
	private String model = "stablediffusion";
	@Builder.Default
	private int steps = 10;
	private String prompt;
	@Builder.Default
	private String size = "512x512";
	@Builder.Default
	private int n = 1;

	public ImgGenRequest(String prompt) {
		this.prompt = prompt;
	}
}
