package blog.cirkle.app.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Statement;
import java.util.Comparator;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("data-import")
@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataImporter implements ApplicationRunner {

	private final ResourceLoader resourceLoader;
	private final SqlExecuter sqlExecuter;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Resource resource = resourceLoader.getResource("classpath:initial-data");
		Path path = resource.getFile().toPath();
		try (Stream<Path> files = Files.walk(path)) {
			files.filter(Files::isRegularFile).sorted(Comparator.comparing(it -> version(it.getFileName().toString())))
					.forEach(sqlExecuter::executeSql);
		}
	}

	private static long version(String fileName) {
		String[] version = fileName.substring(1, fileName.indexOf("__")).split("_");

		int major = Integer.valueOf(version[0]);
		int minor = version.length > 1 ? Integer.valueOf(version[1]) : 0;
		int sub = version.length > 2 ? Integer.valueOf(version[2]) : 0;
		int sub2 = version.length > 3 ? Integer.valueOf(version[3]) : 0;

		return Long.valueOf("%d%03d%03d%03d".formatted(major, minor, sub, sub2));

	}

}

@Slf4j
@Component
@RequiredArgsConstructor
class SqlExecuter {

	private final JdbcTemplate jdbcTemplate;

	@Transactional
	void executeSql(Path path) {
		try {
			log.info("Executing SQL script: {}", path);
			StringBuilder sqlBatch = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
				jdbcTemplate.execute((ConnectionCallback<Object>) con -> {
					String line;
					try (Statement statement = con.createStatement()) {
						while ((line = reader.readLine()) != null) {
							if (line.trim().isEmpty() || line.trim().startsWith("--")) {
								continue; // Skip empty lines and comments
							}

							sqlBatch.append(line).append(" ");

							if (line.trim().endsWith(";")) {
								statement.addBatch(sqlBatch.toString().replace(";", ""));
								sqlBatch.setLength(0); // Clear the batch
							}
						}

						// Execute any remaining SQL
						if (sqlBatch.length() > 0) {
							statement.addBatch(sqlBatch.toString().replace(";", ""));
						}

						statement.executeBatch();

					} catch (Exception e) {
						throw new DataAccessResourceFailureException("Failed to execute SQL script: " + path, e);
					}
					return "";
				});
			}

		} catch (Exception e) {
			log.error("Failed to execute SQL script: {}", path, e);
			throw new RuntimeException("Failed to execute SQL script: " + path, e);
		}
	}
}
