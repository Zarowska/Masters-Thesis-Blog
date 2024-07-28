package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for creating a reaction")
public class CreateReactionDto {

	@NotNull(message = "Value cannot be null") @Schema(description = "Value of the reaction", example = "1")
	private Integer value;

	public static CreateReactionDto of(Integer value) {
		return new CreateReactionDto(value);
	}
}
