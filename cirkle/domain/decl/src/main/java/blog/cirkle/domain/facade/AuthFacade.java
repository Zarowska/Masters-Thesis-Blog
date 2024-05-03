package blog.cirkle.domain.facade;

import blog.cirkle.domain.model.request.EmailValidationRequest;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import blog.cirkle.domain.security.BlogUserDetails;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AuthFacade {

	BlogUserDetails authenticateByUsernameAndPassword(String username, String password);

	BlogUserDetails authenticateByToken(String token);

	String issueTokenForCurrentUser();

	@Transactional
	AuthenticateResponse validateAndLogin(EmailValidationRequest request);
}
