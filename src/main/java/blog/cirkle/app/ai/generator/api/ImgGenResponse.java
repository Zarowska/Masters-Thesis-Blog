package blog.cirkle.app.ai.generator.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImgGenResponse {
	private Long created;
	private String id;
	private List<Generated> data = new ArrayList<>();
	private Usage usage = new Usage();

	@NoArgsConstructor
	@Data
	public static class Generated {
		private String embedding = null;
		private Integer index = -1;
		private String url;
	}

	@NoArgsConstructor
	@Data
	public static class Usage {
		@JsonProperty("prompt_tokens")
		private Integer promptTokens = 0;
		@JsonProperty("completion_tokens")
		private Integer completionTokens = 0;
		@JsonProperty("total_tokens")
		private Integer totalTokens = 0;
	}
}
