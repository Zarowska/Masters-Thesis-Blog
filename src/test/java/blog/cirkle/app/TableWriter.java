package blog.cirkle.app;

import static java.util.stream.Collectors.toMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class TableWriter implements AutoCloseable {
	public static final int MAX_SIZE = 95 * 1024 * 1024;
	private final String fileNamePrefix;
	private final Integer tabIndex;
	private final String tableName;
	private final String idColumnName;
	private final AtomicInteger chunkCounter = new AtomicInteger(0);
	private final AtomicInteger chunkSize = new AtomicInteger(0);
	private Optional<Writer> writer = Optional.empty();

	public void insert(Map<String, String> data) {
		write("INSERT INTO %s (%s) VALUES (%s);\n".formatted(tableName, String.join(", ", data.keySet()),
				String.join(", ", data.values())));
	}

	public void update(Map<String, String> oldData, Map<String, String> newData) {
		if (idColumnName == null) {
			throw new IllegalArgumentException("ID column name is not set");
		}

		String id = oldData.get(idColumnName);
		if (!newData.get(idColumnName).equals(id)) {
			throw new IllegalArgumentException("ID mismatch");
		}
		Map<String, String> change = newData.entrySet().stream()
				.filter(e -> !e.getValue().equals(oldData.get(e.getKey())))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

		write("UPDATE %s SET %s WHERE %s = %s;\n".formatted(tableName, change.entrySet().stream()
				.map(e -> e.getKey() + " = " + e.getValue()).collect(Collectors.joining(", ")), idColumnName, id));
	}

	@SneakyThrows(Exception.class)
	private void write(String row) {
		chunkSize.addAndGet(row.length() + 2);
		if (chunkSize.get() > MAX_SIZE) {
			close();
			chunkSize.set(0);
			write(row);
		} else {
			getWriter().write(row);
		}
	}

	@SneakyThrows(IOException.class)
	private Writer getWriter() {
		if (writer.isEmpty()) {
			String fileName = fileNamePrefix
					+ ("%d_%d__%s.sql".formatted(tabIndex, chunkCounter.incrementAndGet(), tableName));
			writer = Optional.of(new BufferedWriter(new FileWriter(fileName)));
		}
		return writer.get();
	}

	@Override
	public void close() throws Exception {
		writer.ifPresent(w -> {
			try {
				w.flush();
				w.close();
				writer = Optional.empty();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
