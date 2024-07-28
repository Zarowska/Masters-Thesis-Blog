package blog.cirkle.app.api.rest.model;

import blog.cirkle.app.model.entity.ParticipantRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for handling Participant Request")
public class RequestDto {

	@Valid
	@NotNull(message = "Id can't be null") @Schema(description = "ID of participant request")
	private UUID id;

	@Valid
	@NotNull(message = "Sender cannot be null") @Schema(description = "DTO of the Participant who is the sender of the request")
	private ParticipantDto sender;

	@NotNull(message = "Request type cannot be null") @Schema(description = "Type of the Participant Request")
	private ParticipantRequest.ParticipantRequestType type;
}
