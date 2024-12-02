package blog.cirkle.app.service.impl;

import blog.cirkle.app.repository.UserRepository;
import blog.cirkle.app.service.InitialDataLoaderService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Profile("!test")
@Slf4j
public class InitialDataLoaderServiceImpl implements InitialDataLoaderService, ApplicationRunner {

	private final UserRepository userRepository;
	private final JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		if (userRepository.count() == 1) {
			// initial data
			Stream.of("V1_0_1__initial_data_disable_constraints.sql", "V1_0_2__initial_data_participants.sql",
					"V1_0_3__initial_data_users.sql", "V1_0_4__initial_data_images.sql",
					"V1_0_10__initial_data_enable_constraints.sql").map("initial-data/"::concat)
					.map(ClassPathResource::new).forEach(this::executeSqlScript);
		}
	}

	private void executeSqlScript(Resource scriptFile) {
		try {
			// Read the SQL script content
			String sql = new BufferedReader(new InputStreamReader(scriptFile.getInputStream(), StandardCharsets.UTF_8))
					.lines().collect(Collectors.joining("\n"));

			// Execute the SQL script
			jdbcTemplate.execute(sql);
			log.info("SQL script {} executed successfully.", scriptFile.getFilename());
		} catch (Exception e) {
			log.error("Failed to execute SQL script {} : {}", scriptFile.getFilename(), e.getMessage());
		}
	}
}
