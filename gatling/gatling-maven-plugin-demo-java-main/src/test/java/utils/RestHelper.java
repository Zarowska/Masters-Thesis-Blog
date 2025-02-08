package utils;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class RestHelper {

    // Example of a GET request to retrieve a specific post
    public static ChainBuilder getPostById = exec(
            http("GetPostById").get("/api/v1/posts/" +
                    "8888").check(status().is(200)));


}
