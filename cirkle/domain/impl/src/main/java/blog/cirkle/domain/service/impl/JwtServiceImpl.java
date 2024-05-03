package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.properties.JwtConfigurationProperties;
import blog.cirkle.domain.service.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
	private final String issuer;
	private final long ttl;
	private final Algorithm algorithm;
	private JWTVerifier verifier;

	public JwtServiceImpl(Algorithm algorithm, JwtConfigurationProperties properties) {
		this.issuer = properties.getIssuer();
		this.ttl = properties.getTtl();
		this.algorithm = algorithm;
		verifier = JWT.require(algorithm).withIssuer(issuer).acceptLeeway(5).build();
	}

	@Override
	public String issueToken(Object subject, Consumer<JWTCreator.Builder> customizer) {
		JWTCreator.Builder builder = JWT.create().withIssuer(issuer).withSubject(subject.toString());
		customizer.accept(builder);
		builder.withExpiresAt(Instant.now().plusSeconds(ttl)).withNotBefore(Instant.now());

		return builder.sign(algorithm);
	}

	@Override
	public <T> T validateToken(String token, Function<DecodedJWT, T> extractor) {
		DecodedJWT decoded = verifier.verify(token);
		return extractor.apply(decoded);
	}
}
