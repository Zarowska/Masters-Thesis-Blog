package blog.cirkle.app.api.rest.client.endpoints.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Response;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	private static String requestBodyToString(RequestBody requestBody) throws IOException {
		Buffer buffer = new Buffer();
		if (requestBody != null) {
			requestBody.writeTo(buffer);
		}
		return buffer.readUtf8();
	}

	private static String formatJson(String jsonString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(gson.fromJson(jsonString, Object.class));
	}

	@SneakyThrows
	public static <T> void logRequest(String logFileName, Call<T> call, Response<T> response) {
		Request req = call.request();
		RequestBody body = req.body();
		String bodyString = requestBodyToString(body);
		String formattedJson = formatJson(bodyString);
		String path = req.url().encodedPath();
		String content = "%s %s\nRequest:\n%s\nResponse: HTTP-%d\n%s\n\n\n".formatted(req.method(), path, formattedJson,
				response.code(), objectMapper.writeValueAsString(response.body()));
		appendLog(logFileName, content);
	}

	@SneakyThrows
	public static void appendLog(String logFileName, String content) {
		Files.writeString(Paths.get(logFileName), content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}
}
