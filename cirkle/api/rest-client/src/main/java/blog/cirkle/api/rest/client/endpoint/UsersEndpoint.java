
package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.UsersApi;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.response.UserDto;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

@Slf4j
public class UsersEndpoint extends AbstractEndpoint {

	private final UsersApi api;

	public UsersEndpoint(ClientContext context) {
		super(context);
		this.api = context.createApi(UsersApi.class);
	}

	public PaginatedResponse<UserDto> list() {
		return list(0);
	}

	public PaginatedResponse<UserDto> list(int page) {
		return call(api.listUsers(page, null)).body();
	}

	public PaginatedResponse<UserDto> list(int page, int pageSize) {
		return call(api.listUsers(page, pageSize)).body();
	}

	public Optional<UserDto> bySlug(String slug) {
		return optional(call(api.findBySlug(slug)));
	}

	public Optional<UserDto> byId(UUID id) {
		return optional(call(api.findById(id)));
	}

	private <T> Optional<T> optional(Response<T> response) {
		if (!response.isSuccessful() || response.code() == 404) {
			return Optional.empty();
		} else {
			return Optional.of(response.body());
		}
	}

}
