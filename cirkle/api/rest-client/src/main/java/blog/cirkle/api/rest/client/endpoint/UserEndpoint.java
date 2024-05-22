
package blog.cirkle.api.rest.client.endpoint;

import blog.cirkle.api.rest.client.api.UserApi;
import blog.cirkle.api.rest.client.utils.ClientContext;
import blog.cirkle.domain.model.response.UserDto;
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

}
