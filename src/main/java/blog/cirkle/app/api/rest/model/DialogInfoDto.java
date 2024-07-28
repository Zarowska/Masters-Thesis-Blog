package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO that contains the information of a dialog.")
public class DialogInfoDto {

	@NotNull @Schema(description = "The participant object that shows the opponent's info.")
	private ParticipantDto opponent;

	@NotNull @Schema(description = "The count of new messages.")
	private Integer newMessageCount;
}
