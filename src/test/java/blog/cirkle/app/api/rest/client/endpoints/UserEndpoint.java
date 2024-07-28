package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.UserApi;
import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.RequestDto;
import java.util.UUID;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.tika.Tika;
import org.springframework.core.io.ClassPathResource;

public class UserEndpoint extends AbstractEndpoint<UserApi> {
	private Tika tika = new Tika();

	public UserEndpoint(ApiClient.ClientContext context) {
		super(context, UserApi.class);
	}

	public Void acceptParticipantRequest(UUID requestId) {
		return call(api.acceptParticipantRequest(requestId)).body();
	}

	public Void rejectParticipantRequest(UUID requestId) {
		return call(api.rejectParticipantRequest(requestId)).body();
	}

	public PaginatedResponse<ImageDto> listUserImages(Pageable pageable) {
		return call(api.listUserImages(pageable)).body();
	}

	public ImageDto uploadImage(MultipartBody.Part image) {
		return call(api.uploadImage(image)).body();
	}

	public ImageDto uploadImage(String name, MediaType mediaType, byte[] content) {
		RequestBody requestBody = RequestBody.create(content, mediaType);
		MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", name, requestBody);
		return uploadImage(filePart);
	}

	@SneakyThrows
	public ImageDto uploadImage(ClassPathResource resource) {
		byte[] content = resource.getContentAsByteArray();
		MediaType mediaType = MediaType.parse(tika.detect(content));
		return uploadImage(resource.getFilename(), mediaType, content);
	}

	public ParticipantDto getCurrentUserInfo() {
		return call(api.getCurrentUserInfo()).body();
	}

	public PaginatedResponse<RequestDto> listUserRequests(Pageable pageable) {
		return call(api.listUserRequests(pageable)).body();
	}

	public PaginatedResponse<PostDto> listUserPosts(Pageable pageable) {
		return call(api.listUserPosts(pageable)).body();
	}

	public PaginatedResponse<PostDto> getUserFeed(Pageable pageable) {
		return call(api.getUserFeed(pageable)).body();
	}

	public PaginatedResponse<ParticipantDto> listFollowers(Pageable pageable) {
		return call(api.listFollowers(pageable)).body();
	}

	public PaginatedResponse<ParticipantDto> listFriends(Pageable pageable) {
		return call(api.listFriends(pageable)).body();
	}
}
