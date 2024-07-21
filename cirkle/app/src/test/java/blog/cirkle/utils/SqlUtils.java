package blog.cirkle.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlUtils {

	@SneakyThrows
	public static String dump(Connection connection, String tableName) {
		StringBuilder sb = new StringBuilder();
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableName)) {
			try (ResultSet rs = ps.executeQuery()) {
				Map<String, Integer> columns = parseColumns(rs);
				if (rs.next()) {
					sb.append("INSERT INTO %s (%s) VALUES ".formatted(tableName,
							columns.keySet().stream().map(Object::toString).collect(Collectors.joining(","))));
					List<String> rows = new ArrayList<>();
					do {
						StringBuilder row = new StringBuilder().append("(");
						List<String> values = columns.entrySet().stream().map(entry -> formatValue(rs, entry)).toList();

						row.append(String.join(",", values));
						row.append(")");
						rows.add(row.toString());
					} while (rs.next());
					sb.append(String.join(",", rows)).append(";");
				}
			}
		}
		return sb.toString();
	}

	@SneakyThrows
	private static String formatValue(ResultSet rs, Map.Entry<String, Integer> column) {
		Object value;
		int sqlType = column.getValue();

		// Extract and format value based on SQL Type
		switch (sqlType) {
			case -7 :
				value = rs.getBoolean(column.getKey());
				break;
			case 93 :
				value = rs.getTimestamp(column.getKey()).toString();
				break;
			case 1111, 12 :
				value = rs.getString(column.getKey());
				break;
			default :
				throw new UnsupportedOperationException("SqlType: " + sqlType);
		}

		if (rs.wasNull()) {
			value = null;
		}

		// Format the value
		return (value == null) ? "null" : (value instanceof String) ? "'%s'".formatted(value) : value.toString();
	}

	private static @NotNull Map<String, Integer> parseColumns(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		Map<String, Integer> columns = new LinkedHashMap<>();
		for (int i = 0; i < metaData.getColumnCount(); i++) {
			columns.put(metaData.getColumnName(i + 1), metaData.getColumnType(i + 1));
		}
		return columns;
	}
}