package blog.cirkle.app.api.graphql.model.user;

import blog.cirkle.app.api.graphql.model.image.Image;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class GroupProfile {
	private String name;
	private String handle;
	private Image profileImage;
	private Image coverPhoto;
}
