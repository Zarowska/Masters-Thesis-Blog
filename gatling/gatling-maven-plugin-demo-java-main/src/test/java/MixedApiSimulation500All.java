import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import utils.Authentication;

import static io.gatling.javaapi.core.CoreDsl.*;
import static utils.GraphQLHelper.getUser;
import static utils.GraphQLHelper.getUserData;
import static utils.RestHelper.*;

public class MixedApiSimulation500All extends Simulation {

    // Scenario that exercises multiple REST endpoints
//	public static ScenarioBuilder scn = scenario("REST_Api_Scenario").exec(getPostById).pause(1);
//  public static ScenarioBuilder scn = scenario("REST_Api_Scenario").exec(getUsersPosts).pause(1);
    public static ScenarioBuilder scn = scenario("REST+GRAPH_QL_Api_Scenario")
            .exec(getUsers).pause(1)
            .exec(getPostById).pause(1)
            .exec(getUsersPosts).pause(1)
            .exec(getUser).pause(1)
            .exec(getUserData).pause(1);

    static int concurrency = 500;

    {
        // We define injection steps for concurrency:
        setUp(scn.injectOpen(
                rampUsers(concurrency).during(10), // Ramp to 50 in 10s
                constantUsersPerSec(concurrency).during(20)
        )).protocols(Authentication.httpProtocol);
    }
}
