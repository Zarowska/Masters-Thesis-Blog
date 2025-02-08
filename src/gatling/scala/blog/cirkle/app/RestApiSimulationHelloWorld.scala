package blog.cirkle.app

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RestApiSimulationHelloWorld extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")

  val simpleRequest = exec(
    http("Simple_REST_Request")
      .get("/api/hello")
      .check(status.is(200))
  )

  val scn = scenario("REST_HelloWorld")
    .exec(simpleRequest)
    .pause(1)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)


}
