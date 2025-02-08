package blog.cirkle.app

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

class GraphQlSimulation extends Simulation {

  // Add an SLF4J logger
  private val logger = LoggerFactory.getLogger(classOf[GraphQlSimulation])

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
//    .bearerAuth("YOUR_TOKEN_HERE")  // if you’re using Bearer token
    .basicAuth("carlos.martinez42@gmail.com","stringcarlos.martinez42@gmail.com");

  // Our GraphQL query
  val queryString =
    """query {
      |  getUser(userId: "123e4567-e89b-12d3-a456-426614174000") {
      |    name
      |    posts(page: 0, size: 10) {
      |      content {
      |        id
      |        text
      |        images { id uri }
      |        reactions {
      |          reactionValue
      |          reactionCount
      |        }
      |      }
      |    }
      |  }
      |}""".stripMargin

  // Chain that sends the query
  val getUserData = exec { session =>
    // You can log anything from the session or your request
    logger.info("About to send GraphQL request...")
    session
  }
    .exec(
      http("GraphQL_GetPosts")
        .post("/graphql")
        .header("Content-Type", "application/json")
        // Build the JSON body with the query, log it as well
        .body(StringBody(session => {
          val jsonBody = s"""{"query": "${queryString.replace("\n","")}"}"""
          logger.debug(s"Sending GraphQL body => $jsonBody")
          jsonBody
        }))
        .check(status.is(200))
        .check(bodyString.saveAs("graphqlResponse")) // store entire response
    )
    .exec { session =>
      // Now we can log the response from session
      val response = session("graphqlResponse").asOption[String].getOrElse("(no response)")
      logger.info(s"Received GraphQL response: $response")
      session
    }

  // Define scenario
  val scn = scenario("GraphQL_Api_Scenario")
    .exec(getUserData)
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
