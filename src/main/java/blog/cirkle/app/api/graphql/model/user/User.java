package blog.cirkle.app.api.graphql.model.user;

import blog.cirkle.app.api.graphql.model.post.Post;
import blog.cirkle.app.api.graphql.model.post.PostPage;
import blog.cirkle.app.api.graphql.model.relation.RequestPage;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class User {
	private UUID id;
	private String name;
	private String avatarUrl;
	@JsonProperty("isGroup")
	private Boolean isGroup;
	private UserProfile profile;
	private GroupProfile groupProfile;
	private UserPage friends;
	private UserPage followers;
	private RequestPage requests;
	private Post post;
	private PostPage posts;
}
