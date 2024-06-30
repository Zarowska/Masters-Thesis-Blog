package blog.cirkle.domain.model.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class FileDto {
	private UUID id;
	private Integer size;
	private String originalFilename;
	private String mediaType;
	@With
	@Builder.Default
	private String url = null;
}
