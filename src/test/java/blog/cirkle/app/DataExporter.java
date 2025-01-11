package blog.cirkle.app;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataExporter implements AutoCloseable {

	private final DataSource source;
	private final DataSource target;
	private final AtomicInteger counter = new AtomicInteger();

	public DataExporter() {
		source = buildJdbcTemplate("jdbc:postgresql://localhost:6432/cirkle", "devuser", "secret");
		target = buildJdbcTemplate("jdbc:postgresql://localhost:7432/cirkle", "devuser", "secret");
	}

	public static void main(String[] args) {
		try (DataExporter exporter = new DataExporter()) {
			exporter.doExport();
		} catch (Exception e) {
			log.error("Error exporting data", e);
		}
	}

	public void doExport() {
		String outputFolderPrefix = "src/main/resources/initial-data/V1_2_";
		exportIdTable("participants", "id", outputFolderPrefix);
		exportIdTable("system_config", "id", outputFolderPrefix);
		exportIdTable("ai_person_description", "id", outputFolderPrefix);
		exportIdTable("images", "id", outputFolderPrefix);
		exportIdTable("participant_request", "id", outputFolderPrefix);
		exportIdTable("person_interests", "id", outputFolderPrefix);
		exportIdTable("groups", "id", outputFolderPrefix);
		exportIdTable("users", "id", outputFolderPrefix);
		exportNonIdTable("followers", outputFolderPrefix);
		exportNonIdTable("friends", outputFolderPrefix);
		exportNonIdTable("groups_members", outputFolderPrefix);
		exportIdTable("messages", "id", outputFolderPrefix);
		exportIdTable("password_change_requests", "id", outputFolderPrefix);
		exportIdTable("post", "id", outputFolderPrefix);
		exportIdTable("reactions", "id", outputFolderPrefix);
		exportNonIdTable("message_images", outputFolderPrefix);
		exportIdTable("feed", "id", outputFolderPrefix);
		exportNonIdTable("post_images", outputFolderPrefix);
		exportNonIdTable("image_reactions", outputFolderPrefix);
		exportNonIdTable("post_reactions", outputFolderPrefix);
		exportNonIdTable("message_reactions", outputFolderPrefix);
	}

	@SneakyThrows
	private void exportNonIdTable(String tableName, String filePrefix) {
		AtomicInteger insertCounter = new AtomicInteger();
		AtomicInteger updateCounter = new AtomicInteger();

		try (TableReader newTable = new TableReader(tableName, "", source)) {
			try (TableReader oldTable = new TableReader(tableName, "", target)) {
				try (TableWriter writer = new TableWriter(filePrefix, counter.incrementAndGet(), tableName, null)) {
					log.info("Reading table {} existing data", tableName);
					Set<Map<String, String>> oldData = Stream.generate(oldTable).takeWhile(Objects::nonNull)
							.collect(toSet());

					log.info("Exporting new data {} existing data", tableName);
					Stream.generate(newTable).takeWhile(Objects::nonNull).forEach(data -> {
						if (!oldData.contains(data)) {
							writer.insert(data);
							insertCounter.incrementAndGet();
						}
					});
				}
			}
		}
		log.info("Exported {} new records and updated {} records for table {}", insertCounter.get(),
				updateCounter.get(), tableName);
	}

	@SneakyThrows
	private void exportIdTable(String tableName, String idColumn, String filePrefix) {
		AtomicInteger insertCounter = new AtomicInteger();
		AtomicInteger updateCounter = new AtomicInteger();

		try (TableReader newTable = new TableReader(tableName, "", source)) {
			try (TableReader oldTable = new TableReader(tableName, "", target)) {
				try (TableWriter writer = new TableWriter(filePrefix, counter.incrementAndGet(), tableName, idColumn)) {
					log.info("Reading table {} existing data", tableName);
					Map<String, Map<String, String>> oldData = Stream.generate(oldTable).takeWhile(Objects::nonNull)
							.collect(toMap(it -> it.get(idColumn), it -> it));
					log.info("Exporting new data {} existing data", tableName);
					Stream.generate(newTable).takeWhile(Objects::nonNull).forEach(data -> {
						String id = data.get(idColumn);
						Map<String, String> old = oldData.get(id);
						if (old == null) {
							writer.insert(data);
							insertCounter.incrementAndGet();
						} else if (!old.equals(data)) {
							writer.update(old, data);
							updateCounter.incrementAndGet();
						}
					});
				}
			}
		}
		log.info("Exported {} new records and updated {} records for table {}", insertCounter.get(),
				updateCounter.get(), tableName);
	}

	public static DataSource buildJdbcTemplate(String url, String user, String password) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(password);
		return new HikariDataSource(config);
	}

	@Override
	public void close() throws Exception {
		((AutoCloseable) source).close();
		((AutoCloseable) target).close();
	}
}
