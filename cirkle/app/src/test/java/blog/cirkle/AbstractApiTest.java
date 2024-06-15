package blog.cirkle;

import blog.cirkle.api.rest.client.ApiClient;
import lombok.SneakyThrows;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:sql/clear-tables.sql",
		"classpath:sql/sample-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class AbstractApiTest {

	@LocalServerPort
	private int port;

	protected ApiClient createClient() {
		return new ApiClient("http://localhost:" + port);
	}

	protected void asAlice(UserContextConsumer alice) {
		asExistingUser("Alice@cirkle.test", "__Alice123", ctx -> {
			alice.execute(ctx);
		});
	}

	protected void asBob(UserContextConsumer bob) {
		asExistingUser("Bob@cirkle.test", "__Bob123", ctx -> {
			bob.execute(ctx);
		});
	}

	protected void asCarol(UserContextConsumer carol) {
		asExistingUser("Carol@cirkle.test", "__Carol123", ctx -> {
			carol.execute(ctx);
		});
	}

	protected void asDave(UserContextConsumer dave) {
		asExistingUser("Dave@cirkle.test", "__Dave123", ctx -> {
			dave.execute(ctx);
		});
	}

	protected void asEve(UserContextConsumer eve) {
		asExistingUser("Eve@cirkle.test", "__Eve123", ctx -> {
			eve.execute(ctx);
		});
	}

	protected void asMallory(UserContextConsumer mallory) {
		asExistingUser("Mallory@cirkle.test", "__Mallory123", ctx -> {
			mallory.execute(ctx);
		});
	}

	protected void asTrent(UserContextConsumer trent) {
		asExistingUser("Trent@cirkle.test", "__Trent123", ctx -> {
			trent.execute(ctx);
		});
	}

	protected void asOscar(UserContextConsumer oscar) {
		asExistingUser("Oscar@cirkle.test", "__Oscar123", ctx -> {
			oscar.execute(ctx);
		});
	}

	protected void asPeggy(UserContextConsumer peggy) {
		asExistingUser("Peggy@cirkle.test", "__Peggy123", ctx -> {
			peggy.execute(ctx);
		});
	}

	protected void asVictor(UserContextConsumer victor) {
		asExistingUser("Victor@cirkle.test", "__Victor123", ctx -> {
			victor.execute(ctx);
		});
	}

	protected void asNewUser(String email, String firstName, String lastName, String password,
			UserContextConsumer action) {
		ApiClient client = createClient();
		client.registration().register(email, firstName, lastName, password);
		executeAction(client, action);
	}

	protected void asExistingUser(String email, String password, UserContextConsumer action) {
		ApiClient client = createClient();
		client.auth().login(email, password);
		executeAction(client, action);
	}

	@SneakyThrows
	private void executeAction(ApiClient client, UserContextConsumer action) {
		action.execute(client);
	}

	public interface UserContextConsumer {
		void execute(ApiClient client) throws Exception;
	}

}