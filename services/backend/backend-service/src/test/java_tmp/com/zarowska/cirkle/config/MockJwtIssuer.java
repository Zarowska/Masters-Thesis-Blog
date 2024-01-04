package com.zarowska.cirkle.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MockJwtIssuer {

    private final JWSSigner signer;
    private final RSAKey rsaJWK;

    public MockJwtIssuer() throws JOSEException {
        rsaJWK = new RSAKeyGenerator(2048).keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
                .keyID(UUID.randomUUID().toString()) // give the key a unique ID
                .generate();
        signer = new RSASSASigner(rsaJWK);
    }

    public String issueToken() {
        // Google ID token
        // {
        // // These six fields are included in all Google ID Tokens.
        // "iss": "https://accounts.google.com",
        // "sub": "110169484474386276334",
        // "azp":
        // "1008719970978-hb24n2dstb40o45d4feuo2ukqmcc6381.apps.googleusercontent.com",
        // "aud":
        // "1008719970978-hb24n2dstb40o45d4feuo2ukqmcc6381.apps.googleusercontent.com",
        // "iat": "1433978353",
        // "exp": "1433981953",
        //
        // // These seven fields are only included when the user has granted the
        // "profile" and
        // // "email" OAuth scopes to the application.
        // "email": "testuser@gmail.com",
        // "email_verified": "true",
        // "name" : "Test User",
        // "picture":
        // "https://lh4.googleusercontent.com/-kYgzyAWpZzJ/ABCDEFGHI/AAAJKLMNOP/tIXL9Ir44LE/s99-c/photo.jpg",
        // "given_name": "Test",
        // "family_name": "User",
        // "locale": "en"
        // }

        try {
            // Prepare JWT with claims set
            long now = System.currentTimeMillis();
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject("110169484474386276334")
                    .issuer("https://accounts.google.com")
                    .audience("1008719970978-hb24n2dstb40o45d4feuo2ukqmcc6381.apps.googleusercontent.com")
                    .issueTime(new Date(now)).expirationTime(new Date(now + 1000 * 60 * 60)) // expires in 1 hour
                    .claim("email", "testuser@gmail.com").claim("email_verified", true).claim("name", "Test User")
                    .claim("picture", "https://i.pravatar.cc/150?img=6").claim("given_name", "Test")
                    .claim("family_name", "User").claim("locale", "en").build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(), claimsSet);

            // Compute the RSA signature
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
