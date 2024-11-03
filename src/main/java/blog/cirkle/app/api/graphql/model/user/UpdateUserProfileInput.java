package blog.cirkle.app.api.graphql.model.user;

import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UpdateUserProfileInput {
	private String name;
	private String handle;
	private UUID profileImageId;
	private UUID coverPhotoImageId;
	private String phoneNumber;
	private String bio;
	private String country;
	private String city;
}
