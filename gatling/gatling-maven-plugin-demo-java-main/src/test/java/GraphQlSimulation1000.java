import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static utils.Authentication.httpProtocol;
import static utils.GraphQLHelper.listUsers;
//import static utils.GraphQLHelper.getUserData;

public class GraphQlSimulation1000 extends Simulation {

    //  ScenarioBuilder scn = scenario("GraphQL_Api_Scenario").exec(getUserData).pause(1);
    ScenarioBuilder scn = scenario("GraphQL_Api_Scenario").exec(listUsers).pause(1);


    static int concurrency = 1000;

    {
        setUp(
                scn.injectOpen(
                        rampUsers(concurrency).during(10),
                        constantUsersPerSec(concurrency).during(20)
                )
        ).protocols(httpProtocol);
    }


}
