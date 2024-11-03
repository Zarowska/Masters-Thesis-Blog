package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.user.User;
import blog.cirkle.app.api.rest.AbstractApiTest;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

class UserControllerTest extends AbstractApiTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void users() {
		asAlice(alice -> {
			JsonObject response = alice.graphql.execute("", """
					query {
					   getCurrentUserInfo {
					     id
					     name
					     avatarUrl
					     isGroup
					     friends(page: 0, size: 5) {
					     	content {
							   id
					           name
					           avatarUrl
					           isGroup
					     	}
					     	pageInfo {
					     		totalElements
					        	totalPages
					        	pageNumber
					        	pageSize
					        	first
					        	last
					     	}
					     }
					     followers(page: 0, size: 5) {
					     	content {
							   id
					           name
					           avatarUrl
					           isGroup
					     	}
					     	pageInfo {
					     		totalElements
					        	totalPages
					        	pageNumber
					        	pageSize
					        	first
					        	last
					     	}
					     }
					     requests(page: 0, size: 5) {
						     	content {
					     	id
					     	sender {
							   id
					           name
					           avatarUrl
					           isGroup
					     	}
					     	type
					     	}
					     	pageInfo {
					     		totalElements
					        	totalPages
					        	pageNumber
					        	pageSize
					        	first
					        	last
					     	}
					     }
					     profile {
					        name
					        handle
					        profileImage {
					            id
					            uri
					            reactions {
					                reactionValue
					                reactionCount
					                participants {
					                  id
					                }
					            }
					        }
					        coverPhoto {
					            id
					            uri
					            reactions {
					                reactionValue
					                reactionCount
					                participants {
					                  id
					                }
					            }
					        }
					        phoneNumber
					        bio
					        country
					        city
					     }
					  }
					}
					""");
			User user = mapper.readValue(
					response.getAsJsonObject("data").getAsJsonObject("getCurrentUserInfo").toString(), User.class);

			System.out.println(user);

		});

	}
}