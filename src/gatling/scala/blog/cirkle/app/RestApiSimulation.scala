package blog.cirkle.app

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

class RestApiSimulation extends Simulation {

  private val logger = LoggerFactory.getLogger(classOf[RestApiSimulation])

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    //.bearerAuth("YOUR_TOKEN_HERE")  // adjust as needed
    .basicAuth("carlos.martinez42@gmail.com","stringcarlos.martinez42@gmail.com");

  // An example GET request chain
  val getPostById = exec { session =>
    logger.info("About to GET a specific post by ID...")
    session
  }
    .exec(
      http("GetPostById")
        .get("/api/v1/posts/123e4567-e89b-12d3-a456-426614174000")
        .check(status.is(200))
        .check(bodyString.saveAs("postResponse"))
    )
    .exec { session =>
      val resp = session("postResponse").asOption[String].getOrElse("No body")
      logger.info(s"Received GET response: $resp")
      session
    }

  val scn = scenario("REST_Api_Scenario")
    .exec(getPostById)
    .pause(1)

  setUp(
    scn.inject(
      rampUsers(50).during(10),
      constantUsersPerSec(50).during(20),
      rampUsers(50).during(10),
      constantUsersPerSec(100).during(20),
      rampUsers(100).during(10),
      constantUsersPerSec(200).during(20),
      rampUsers(300).during(10),
      constantUsersPerSec(500).during(20),
      rampUsers(500).during(10),
      constantUsersPerSec(1000).during(20)
    )
  ).protocols(httpProtocol)
}
