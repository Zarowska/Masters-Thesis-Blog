package blog.cirkle.app.api.rest.client.api;

import java.util.UUID;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImagesApi {
	@GET("/public/api/images/{imageId}")
	Call<ResponseBody> getImageByID(@Path("imageId") UUID imageId);
}