package blog.cirkle.app.api.graphql.model.user;

import blog.cirkle.app.api.graphql.model.image.Image;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UserProfile {
	private String name;
	private String handle;
	private Image profileImage;
	private Image coverPhoto;
	private String phoneNumber;
	private String bio;
	private String country;
	private String city;
}
