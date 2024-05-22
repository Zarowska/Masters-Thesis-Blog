package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.FilesApi;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.api.rest.client.utils.Lazy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.tika.Tika;
import retrofit2.Response;

public class FilesEndpoint extends AbstractEndpoint {
	private Lazy<Tika> tika = new Lazy<>(Tika::new);

	private final FilesApi api;

	public FilesEndpoint(ClientContext context) {
		super(context);
		this.api = context.createApi(FilesApi.class);
	}

	public String upload(String name, MediaType mediaType, byte[] content) {
		RequestBody requestBody = RequestBody.create(mediaType, content);
		MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", name, requestBody);
		Response<ResponseBody> response = call(api.upload(filePart));
		return response.headers().get("Location");
	}

	public String upload(File file) throws IOException {
		MediaType mediaType = MediaType.parse(tika.get().detect(file));
		return upload(file.getName(), mediaType, Files.readAllBytes(file.toPath()));
	}

	public Optional<byte[]> byId(UUID imageId, Integer height, Integer width) throws IOException {
		Response<ResponseBody> response = call(api.byId(imageId, height, width));
		if (!response.isSuccessful() || response.code() == 404) {
			return Optional.empty();
		}
		return Optional.of(response.body().bytes());
	}
}
