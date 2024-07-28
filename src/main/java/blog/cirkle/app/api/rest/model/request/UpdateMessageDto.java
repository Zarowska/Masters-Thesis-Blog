package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data transfer object for updating a message")
public class UpdateMessageDto implements MediaUpdate {
	@Builder.Default
	@Schema(description = "Text content of the messsage")
	@NotBlank(message = "Message text may not be blank")
	private String text = "";

	@Builder.Default
	@Schema(description = "List of images linked with the message")
	@NotEmpty(message = "List of images may not be empty")
	private List<UUID> images = new ArrayList<>();
}
