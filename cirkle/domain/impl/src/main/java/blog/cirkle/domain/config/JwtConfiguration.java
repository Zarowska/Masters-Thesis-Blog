package blog.cirkle.domain.config;

import blog.cirkle.domain.properties.JwtConfigurationProperties;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

	@Bean
	Algorithm algorithm(JwtConfigurationProperties config) {
		switch (config.getAlgorithm()) {
			case "RSA256" -> {
				assert config.getPublicKey() != null;
				assert config.getPrivateKey() != null;
				return Algorithm.RSA256(decodeRsaPublicKey(config.getPublicKey()),
						decodeRsaPrivateKey(config.getPrivateKey()));
			}
			case "HMAC256" -> {
				assert config.getSecret() != null;
				return Algorithm.HMAC256(config.getSecret());
			}
			default -> throw new IllegalStateException("Unsupported algorithm: " + config.getAlgorithm());
		}
	}

	private RSAPublicKey decodeRsaPublicKey(String base64PublicKey) {
		try {
			byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
			X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPublicKey) keyFactory.generatePublic(publicSpec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private RSAPrivateKey decodeRsaPrivateKey(String base64PrivateKey) {
		try {
			byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);
			PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(privateSpec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
