package blog.cirkle.app;

import static java.sql.Types.BIT;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class TableReader implements Supplier<Map<String, String>>, AutoCloseable {
	private final String tableName;
	private final String options;
	private final DataSource source;

	private Optional<Connection> connection = Optional.empty();
	private Optional<Statement> statement = Optional.empty();
	private Optional<ResultSet> resultSet = Optional.empty();
	private Map<String, UnsafeFunction<ResultSet, String>> mappers = new LinkedHashMap<>();

	@Override
	@SneakyThrows
	public Map<String, String> get() {
		ResultSet rs = getResultSet();
		if (rs.next()) {
			return mappers.entrySet().stream().collect(LinkedHashMap::new, (map, entry) -> {
				try {
					map.put(entry.getKey(), entry.getValue().apply(rs));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, LinkedHashMap::putAll);
		} else {
			return null;
		}
	}

	@SneakyThrows(SQLException.class)
	private ResultSet getResultSet() {
		if (resultSet.isEmpty()) {
			synchronized (this) {
				if (resultSet.isEmpty()) {
					connection = Optional.of(source.getConnection());
					statement = Optional.of(connection.get().createStatement());
					resultSet = Optional
							.of(getStatement().executeQuery("SELECT * FROM %s %s".formatted(tableName, options)));
					ResultSet rs = resultSet.get();
					ResultSetMetaData metaData = rs.getMetaData();
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						String name = getColumnName(metaData, i);
						int type = metaData.getColumnType(i);
						switch (type) {
							case Types.INTEGER -> mappers.put(name, resultSet -> {
								int anInt = resultSet.getInt(name);
								return resultSet.wasNull() ? "NULL" : String.valueOf(anInt);
							});
							case Types.BIGINT -> mappers.put(name, resultSet -> {
								long anLong = resultSet.getLong(name);
								return resultSet.wasNull() ? "NULL" : String.valueOf(anLong);
							});
							case Types.TIMESTAMP ->
								mappers.put(name, resultSet -> wrap.apply(resultSet.getTimestamp(name)));
							case Types.OTHER -> mappers.put(name, resultSet -> wrap.apply(resultSet.getObject(name)));
							case Types.VARCHAR, Types.CHAR ->
								mappers.put(name, resultSet -> wrap.apply(resultSet.getString(name)));
							case Types.BINARY, Types.VARBINARY ->
								mappers.put(name, resultSet -> binary.andThen(wrap).apply(resultSet.getBytes(name)));
							case BIT -> mappers.put(name, resultSet -> {
								boolean aBoolean = resultSet.getBoolean(name);
								return resultSet.wasNull() ? "NULL" : String.valueOf(aBoolean);
							});
							default -> throw new IllegalArgumentException("Unsupported type: " + type);
						}
					}
				}
			}

		}
		return resultSet.get();
	}

	private static Function<Object, String> wrap = obj -> obj == null
			? "NULL"
			: "'%s'".formatted(obj.toString().replace("'", "''"));

	private static Function<byte[], String> toHex = value -> {
		StringBuilder sb = new StringBuilder();
		for (byte b : value) {
			sb.append(String.format("%02x", b).toUpperCase(Locale.ROOT));
		}
		return sb.toString();
	};
	private static Function<byte[], String> binary = value -> "E" + wrap.apply("\\\\x" + toHex.apply(value));

	private static String getColumnName(ResultSetMetaData metaData, int i) throws SQLException {
		return metaData.getColumnName(i);
	}

	@SneakyThrows(SQLException.class)
	private Statement getStatement() {
		if (statement.isEmpty()) {
			statement = Optional.of(getConnection().createStatement());
		}
		return statement.get();
	}

	@SneakyThrows(SQLException.class)
	private Connection getConnection() {
		if (connection.isEmpty()) {
			connection = Optional.of(source.getConnection());
		}
		return connection.get();
	}

	@Override
	public void close() throws Exception {
		resultSet.ifPresent(this::autoClose);
		statement.ifPresent(this::autoClose);
		connection.ifPresent(this::autoClose);
	}

	private void autoClose(@NotNull AutoCloseable autoCloseable) {
		try {
			autoCloseable.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

interface UnsafeFunction<T, R> {
	R apply(T t) throws Exception;
}