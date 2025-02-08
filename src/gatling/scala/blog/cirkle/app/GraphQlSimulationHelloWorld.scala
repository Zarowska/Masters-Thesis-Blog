package blog.cirkle.app

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GraphQlSimulationHelloWorld extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")

  val simpleRequest = exec(
    http("Simple_GraphQL_Request")
      .post("/graphql")
      .header("Content-Type", "application/json")
      .body(StringBody("""{"query": "{ hello }"}"""))
      .check(status.is(200))
  )

  val scn = scenario("GraphQL_HelloWorld")
    .exec(simpleRequest)
    .pause(1)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)

}
