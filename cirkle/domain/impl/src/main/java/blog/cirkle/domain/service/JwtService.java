package blog.cirkle.domain.service;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.function.Consumer;
import java.util.function.Function;

public interface JwtService {
	String issueToken(Object subject, Consumer<JWTCreator.Builder> customizer);

	<T> T validateToken(String token, Function<DecodedJWT, T> extractor);
}
