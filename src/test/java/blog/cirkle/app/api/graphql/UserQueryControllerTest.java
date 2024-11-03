package blog.cirkle.app.api.graphql;

import static org.junit.jupiter.api.Assertions.*;

import blog.cirkle.app.api.rest.AbstractApiTest;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

class UserQueryControllerTest extends AbstractApiTest {

	@Test
	void users() {
		asAlice(alice -> {
			JsonObject response = alice.graphql.execute("MyQuery", """
					query MyQuery {
					  user(id:"a2f1a990-8d45-4caf-80fc-2b488c0ed97e") {
					    id
					    name
					    avatarUrl
					    isGroup
					  }
					  users(limit:10, offset:0) {
					    id
					    name
					    avatarUrl
					    isGroup
					  }
					}
					""");
			System.out.println(response);

		});

	}
}