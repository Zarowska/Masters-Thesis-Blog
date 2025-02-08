package blog.cirkle.app;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class GraphQlSimulation extends Simulation {

	HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").acceptHeader("application/json")
			// .bearerAuth("YOUR_TOKEN_HERE")
			// .basicAuth("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJzdWIiOiI5OWU2NzAwYy01OTNlLTQ5N2MtOGI2YS05ZjAwYjUzZTA1ZmYiLCJleHAiOjE3Mzg3MTc5NDMsIm5iZiI6MTczODcxNDM0M30.IOuFv5EVTeA-zlc2NX4lp2nDKFszt95TPnJ-DebFgkJMtsXLydZG1p0apjow8wsIQfGUwrqvyujXuJXjJ3Iw-t9TkMQxQ3ZxyxojWSHo_JtJSEVD0h8NiTV-ZmLPHgUEVWBQJxzdEuzxrRS_07jf_Iq9yUmJ3E10S00rPnJaVdECL7YVKFykuTxgUxlMHHEotSf-5el_cXWI6gnGfNEmmCuX8CS7qKNd94zG-2l6eno4JKlgnXzJftpAxqdyVIE5J2SXJItyiFHVdUFUkRooDaDf9qLm_-misEsVIvHaWkQ8qsAuoJerf7BggmgmNFtIX8Dpps51bG5SpsmZUuW9sg")
			.basicAuth("user@example.com", "superSecurePass123");

	// Our GraphQL query
	String queryString = """
			query {
			  getUser(userId: "123e4567-e89b-12d3-a456-426614174000") {
			    name
			    posts(page: 0, size: 10) {
			      content {
			        id
			        text
			        images { id uri }
			        reactions {
			          reactionValue
			          reactionCount
			        }
			      }
			    }
			  }
			}
			""";

	ChainBuilder getUserData = exec(http("GraphQL_GetPosts").post("/graphql").header("Content-Type", "application/json")
			.body(StringBody("{\"query\": \"" + queryString.replace("\n", "") + "\"}")).check(status().is(200)));

	ScenarioBuilder scn = scenario("GraphQL_Api_Scenario").exec(getUserData).pause(1);

	{
		setUp(scn.injectOpen(rampUsers(50).during(10), constantUsersPerSec(50).during(20),

				rampUsers(50).during(10), constantUsersPerSec(100).during(20),

				rampUsers(100).during(10), constantUsersPerSec(200).during(20),

				rampUsers(300).during(10), constantUsersPerSec(500).during(20),

				rampUsers(500).during(10), constantUsersPerSec(1000).during(20))).protocols(httpProtocol);
	}
}
