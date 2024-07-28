package blog.cirkle.app.api.rest.model;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for comments related operations.")
public class CommentDto {
	@NotNull(message = "Id cannot be null.") @Schema(description = "Unique identifier of the comment.")
	private Long id;

	@NotNull(message = "Post Id cannot be null.") @Schema(description = "Unique identifier of the post connected with the comment.")
	private UUID postId;

	@Schema(description = "Id of parent comment. Can be null if comment is top level.")
	private Long parentCommentId;

	@NotNull(message = "Author cannot be null.") @Schema(description = "Participant who posted the comment.")
	private ParticipantDto author;

	@NotBlank(message = "Comment text cannot be blank.")
	@Schema(description = "Actual text content of the comment.")
	@Builder.Default
	private String text = "";

	@Schema(description = "List of UUIDs representing images associated with the comment.")
	@Builder.Default
	private List<ImageDto> images = new ArrayList<>();

	@NotNull(message = "Reactions cannot be null.") @Schema(description = "Reaction data associated with the comment.")
	@Builder.Default
	private ReactionsDto reactions = new ReactionsDto();
}
