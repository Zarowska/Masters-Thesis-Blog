package blog.cirkle.api.rest.client.api;

import java.util.UUID;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface FilesApi extends Api {
	@Streaming
	@GET("/api/v1/images/{id}")
	Call<ResponseBody> byId(@Path("id") UUID id, @Query("height") Integer height, @Query("width") Integer width);

	@Multipart
	@POST("/api/v1/images")
	Call<ResponseBody> upload(@Part MultipartBody.Part image);
}
