package blog.cirkle.app.facade;

import blog.cirkle.app.model.entity.User;

public interface AuthFacade {
	User authenticateByToken(String token);

	User authenticateByUsernameAndPassword(String username, String password);

	String issueTokenForCurrentUser();
}
