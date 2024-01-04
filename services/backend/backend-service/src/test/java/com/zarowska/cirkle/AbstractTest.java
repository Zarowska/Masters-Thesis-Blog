package com.zarowska.cirkle;

import com.zarowska.cirkle.api.client.BlogClient;
import com.zarowska.cirkle.config.MockJwtIssuer;
import com.zarowska.cirkle.facade.mapper.UserEntityMapper;
import com.zarowska.cirkle.utils.TestUserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

}
