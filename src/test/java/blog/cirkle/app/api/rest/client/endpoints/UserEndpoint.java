package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.UserApi;
import blog.cirkle.app.api.rest.client.endpoints.utils.PageableQueryMapConverter;
import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.UpdateUserProfileDto;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;

public class UserEndpoint extends AbstractEndpoint<UserApi> {
	private final Tika tika = new Tika();

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
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.listUserImages(pageableMap)).body();
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
	public ImageDto uploadImage(Resource resource) {
		byte[] content = resource.getContentAsByteArray();
		MediaType mediaType = MediaType.parse(tika.detect(content));
		return uploadImage(resource.getFilename(), mediaType, content);
	}

	public ParticipantDto getCurrentUserInfo() {
		return call(api.getCurrentUserInfo()).body();
	}

	public PaginatedResponse<RequestDto> listUserRequests(Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.listUserRequests(pageableMap)).body();
	}

	public PaginatedResponse<PostDto> listUserPosts(Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.listUserPosts(pageableMap)).body();
	}

	public PaginatedResponse<PostDto> getUserFeed(Pageable pageable) {

		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.getUserFeed(pageableMap)).body();
	}

	public PaginatedResponse<ParticipantDto> listFollowers(Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.listFollowers(pageableMap)).body();
	}

	public PaginatedResponse<ParticipantDto> listFriends(Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.listFriends(pageableMap)).body();
	}

	public UserProfileDto updateUserProfile(UpdateUserProfileDto userProfileDto) {
		return call(api.updateProfile(userProfileDto)).body();
	}
}
