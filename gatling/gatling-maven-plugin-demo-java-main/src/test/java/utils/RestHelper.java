package utils;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class RestHelper {

    // Example of a GET request to retrieve a specific post
//    public static ChainBuilder getPostById = exec(
//            http("REST_GetPostById").get("/api/v1/posts/" +
//                    "6a803ea9-d828-455f-8713-e037fcedf09e").check(status().is(200)));

    public static ChainBuilder getUsersPosts = exec(
            http("REST_GetUsersPosts").get("/api/v1/users/" +
                    "a14092e3-cf86-4337-b75e-b76df514385f" + "/posts?page=0&size=10").check(status().is(200)));


    public static ChainBuilder listUsers = exec(
            http("REST_GetUsers").get("/api/v1/users?page=0&size=100").check(status().is(200)));

}
