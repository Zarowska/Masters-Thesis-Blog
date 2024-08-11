package blog.cirkle.app.api.graphql;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserQueryResolverTest {

    private static final String GRAPHQL_QUERY_RESOURCE_REQUEST_PATH = "graphql/resolver/query/request/%s.graphql";
    private static final String GRAPHQL_QUERY_RESOURCE_RESPONSE_PATH = "graphql/resolver/query/request/%.json";

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void userQueryResolverTest() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource(format(GRAPHQL_QUERY_RESOURCE_REQUEST_PATH, "list_users"));

        System.out.println(response.getRawResponse().getBody());
    }

    @SneakyThrows
    private String read(String location) {
        return IOUtils.toString(
                new ClassPathResource(location).getInputStream(), StandardCharsets.UTF_8
        );
    }
}