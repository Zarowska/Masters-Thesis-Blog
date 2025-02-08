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

    static String queryString = GraphQLHelper.query2json(
            """
                    query {
                      getUser(userId: "54c14d99-e974-49a7-9cdc-56c5d0f60167") {
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

    public static ChainBuilder getUserData = exec(http("GraphQL_GetPosts").post("/graphql").header("Content-Type", "application/json")
            .body(StringBody(queryString)).check(status().is(200)));

}
