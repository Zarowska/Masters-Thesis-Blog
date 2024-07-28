package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for group creation request")
public class CreateGroupDto {

	@NotBlank
	@Schema(description = "Name of the group")
	private String name;

	@Builder.Default
	@Schema(description = "Privacy setting of the group. Default is set to true")
	private Boolean isPrivate = true;
}
