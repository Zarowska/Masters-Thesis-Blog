package com.zarowska.cirkle;

import com.zarowska.cirkle.api.client.BlogClient;
import com.zarowska.cirkle.config.MockJwtIssuer;
import com.zarowska.cirkle.facade.mapper.UserEntityMapper;
import com.zarowska.cirkle.utils.TestUserContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AbstractTest {

	@LocalServerPort
	protected int port;

	@Autowired
	MockJwtIssuer jwtIssuer;

	@Autowired
	UserEntityMapper mapper;

	protected TestUserContext context(String userName, String email, String avatarUrl) {
		BlogClient api = BlogClient.local(port);
		api.setBearerToken(jwtIssuer.issueToken(userName, email, avatarUrl));
		return new TestUserContext(api);
	}

	public static Resource getFileFromResource(String filename) {
		try {
			return new ByteArrayResource(getFileFromResourcesAsByteArray(filename)) {
				@Override
				public String getFilename() {
					int slashIndex = filename.lastIndexOf('/');
					return filename.substring(slashIndex + 1);
				}
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] getFileFromResourcesAsByteArray(String filename) throws IOException {
		try (InputStream inputStream = Optional
				.ofNullable(AbstractTest.class.getClassLoader().getResourceAsStream(filename))
				.orElseThrow(() -> new FileNotFoundException(filename))) {
			return inputStream.readAllBytes();
		}
	}
}
