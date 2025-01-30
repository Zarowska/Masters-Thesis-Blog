package blog.cirkle.app.ai.generator.api;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDescription {
	private String post;
	@Builder.Default
	private List<Image> images = new ArrayList<>();

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Image {
		private String type;
		private String setting;
		private String activity;
	}
}