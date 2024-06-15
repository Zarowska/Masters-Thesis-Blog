package blog.cirkle;

import static java.util.stream.Collectors.toMap;

import blog.cirkle.api.rest.client.ApiClient;
import blog.cirkle.domain.model.newModel.UserDto;
import blog.cirkle.utils.SqlUtils;
import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {"classpath:sql/clear-tables.sql"})
@Disabled // do not commit uncommented
public class SampleDataGenerator extends AbstractApiTest {

	@Autowired
	DataSource dataSource;

	@Test
	public void testData() throws Exception {
		generateSampleData();
		try (Connection connection = dataSource.getConnection()) {
			List<String> tables = List.of("objects", "participants", "users", "email_validations", "groups",
					"relations", "relation_requests", "resources", "image_files", "posts", "texts", "images",
					"resources_resources", "reactions", "comments", "feed");

			String script = tables.stream().map(name -> SqlUtils.dump(connection, name)).filter(it -> !it.isBlank())
					.collect(Collectors.joining("\n"));

			FormatConfig cfg = FormatConfig.builder().indent("    ").uppercase(true).build();
			String result = SqlFormatter.format(script, cfg);
			Files.write(Paths.get("./src/test/resources/sql/sample-data.sql"), result.getBytes(StandardCharsets.UTF_8));
		}
	}

	private void generateSampleData() {
		Map<String, UserDto> users = List
				.of("Alice", "Bob", "Carol", "Dave", "Eve", "Mallory", "Trent", "Oscar", "Peggy", "Victor").stream()
				.map(this::registerUser).map(ApiClient::getCurrentUser)
				.collect(toMap(UserDto::getFirstName, Function.identity()));

		// alice and bob sends friendship and follow requests to all

		asAlice(client -> users.entrySet().stream().filter(it -> !it.getKey().equals("Alice")).map(Map.Entry::getValue)
				.forEach(dto -> {
					client.user(dto.getId()).friend();
					client.user(dto.getId()).follow();
				}));

		asBob(client -> users.entrySet().stream().filter(it -> !it.getKey().equals("Bob")).map(Map.Entry::getValue)
				.forEach(dto -> {
					client.user(dto.getId()).friend();
					client.user(dto.getId()).follow();
				}));
	}

	@SneakyThrows
	private ApiClient registerUser(String name) {
		ApiClient client = createClient();
		client.registration().register("%s@cirkle.test".formatted(name), name, "Testson", "__%s123".formatted(name));
		return client;
	}
}