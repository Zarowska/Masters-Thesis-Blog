
package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.UserApi;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.UserDto;
import blog.cirkle.domain.model.response.PostDto;
import blog.cirkle.domain.model.response.RelationRequestDto;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEndpoint extends AbstractEndpoint {

	private final UserApi api;

	public UserEndpoint(ClientContext context) {
		super(context);
		this.api = context.createApi(UserApi.class);
	}

	public UserDto current() {
		return call(api.currentUser()).body();
	}

	public PaginatedResponse<RelationRequestDto> relationRequests(Integer page, Integer size) {
		return call(api.requests(page, size)).body();
	}

	public void acceptRequest(UUID requestId) {
		call(api.acceptRequest(requestId));
	}

	public void rejectRequest(UUID requestId) {
		call(api.rejectRequest(requestId));
	}

	public PaginatedResponse<PostDto> feed(int page, int pageSize) {
		return call(api.feed(page, pageSize)).body();
	}
}
