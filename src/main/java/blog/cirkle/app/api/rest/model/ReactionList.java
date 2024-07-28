package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for the list of reactions")
public class ReactionList {

	@NotNull(message = "Reaction value cannot be null") @Schema(description = "Value of the reaction")
	private Integer reactionValue;

	@NotNull(message = "Reaction count cannot be null") @Schema(description = "Count of the reactions")
	private Integer reactionCount;

	@Valid
	@NotNull(message = "Participants list cannot be null") @Schema(description = "List of participants who reacted")
	List<ParticipantDto> participants;
}
