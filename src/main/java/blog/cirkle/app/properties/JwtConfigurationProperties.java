package blog.cirkle.app.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtConfigurationProperties {
	private String issuer;
	private Long ttl;
	private String algorithm;
	private String publicKey;
	private String privateKey;
	private String secret;

}
