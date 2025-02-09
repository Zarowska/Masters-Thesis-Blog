import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static utils.Authentication.httpProtocol;
import static utils.GraphQLHelper.getUserData;

public class GraphQlSimulation20 extends Simulation {

    ScenarioBuilder scn = scenario("GraphQL_Api_Scenario").exec(getUserData).pause(1);

    static int concurrency = 20;

    {
        setUp(
                scn.injectOpen(
                        rampUsers(concurrency).during(10),
                        constantUsersPerSec(concurrency).during(20)
                )
        ).protocols(httpProtocol);
    }
}
