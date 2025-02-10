package utils;

import io.gatling.javaapi.core.ChainBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GraphQLHelper {

    public static String query2json(String query) {
        String json = Arrays.stream(query.split("\n"))
                .map(line -> line.replace("\"", "\\\""))
                .collect(Collectors.joining("\\n"));
        return "{\"query\":\"" + json + "\", \"variables\":{}}";
    }

    static String queryStringGetUserPosts = GraphQLHelper.query2json(
            """
                    query {
                      getUser(userId: "a14092e3-cf86-4337-b75e-b76df514385f") {
                        name
                        posts(page: 0, size: 10) {
                          content {
                            id
                            text
                            images {
                              id
                              uri
                            }
                            reactions {
                              reactionValue
                              reactionCount
                            }
                          }
                        }
                      }
                    }

                    """
    );

    public static ChainBuilder getUserPosts = exec(http("GraphQL_GetUserPosts").post("/graphql").header("Content-Type", "application/json")
            .body(StringBody(queryStringGetUserPosts)).check(status().is(200)));


    static String queryStringListUsers = GraphQLHelper.query2json(
            """
                query {
                    listUsers(page: 0, size: 100) {
                        content {
                           id
                           name
                            avatarUrl
                            isGroup
                        }
                    }
                }
                    
            """
    );


    public static ChainBuilder listUsers = exec(http("GraphQL_GetUsers").post("/graphql").header("Content-Type", "application/json")
            .body(StringBody(queryStringListUsers)).check(status().is(200)));

}
