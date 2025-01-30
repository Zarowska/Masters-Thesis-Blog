package blog.cirkle.app.ai.generator.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImageSettingResponse {
	private String setting;
	private String activity;
}
