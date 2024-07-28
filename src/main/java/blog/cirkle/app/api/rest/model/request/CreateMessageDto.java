package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Data Transfer Object for creating message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMessageDto {
	@Schema(description = "Text of the message")
	@NotBlank(message = "Text field must not be blank")
	@Builder.Default
	private String text = "";

	@Schema(description = "List of image UUID's")
	@NotNull(message = "Images field must not be null") @Builder.Default
	private List<UUID> images = new ArrayList<>();
}
