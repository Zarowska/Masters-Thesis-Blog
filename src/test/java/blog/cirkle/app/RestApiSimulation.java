package blog.cirkle.app;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class RestApiSimulation extends Simulation {

	HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080") // Change if needed
			.acceptHeader("application/json")
	// .bearerAuth("YOUR_TOKEN_HERE")
	; // Or set dynamically

	// Example of a GET request to retrieve a specific post
	ChainBuilder getPostById = exec(
			http("GetPostById").get("/api/v1/posts/123e4567-e89b-12d3-a456-426614174000").check(status().is(200)));

	// Scenario that exercises multiple REST endpoints
	ScenarioBuilder scn = scenario("REST_Api_Scenario").exec(getPostById).pause(1);

	{
		// We define injection steps for concurrency:
		// - rampUsers(50) over 10s, hold for 20s
		// - rampUsers(100-50=50 more, total 100) over 10s, hold for 20s
		// - rampUsers(100 more to 200)...
		// - etc., up to 1000
		// You can adapt intervals as needed
		setUp(scn.injectOpen(rampUsers(50).during(10), // Ramp to 50 in 10s
				constantUsersPerSec(50).during(20),

				rampUsers(50).during(10), // Ramp to 100 in next 10s
				constantUsersPerSec(100).during(20),

				rampUsers(100).during(10), // Ramp to 200
				constantUsersPerSec(200).during(20),

				rampUsers(300).during(10), // Ramp to 500
				constantUsersPerSec(500).during(20),

				rampUsers(500).during(10), // Ramp to 1000
				constantUsersPerSec(1000).during(20))).protocols(httpProtocol);
	}
}
