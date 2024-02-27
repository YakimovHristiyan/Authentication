package sample.authentication.config;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ConfigurationProperties("auth.api")
@Data
public class AuthApiProperties {

    private long jwtExpirationInMilliSeconds;
    private long jwtRefreshExpirationInMilliSeconds;
    private String publicKeyPath;
    private String privateKeyPath;

    @SneakyThrows
    public RSAPrivateKey readPrivateKey(String filepath) {
        final var pemPrivateKey = new String(getClass().getResourceAsStream(filepath).readAllBytes());
        final var base64PrivateKey = extractBase64KeyFromPEM(pemPrivateKey);
        final var privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);
        final var keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    @SneakyThrows
    public RSAPublicKey readPublicKey(String filepath) {
        final var pemPublicKey = new String(getClass().getResourceAsStream(filepath).readAllBytes());
        final var base64PublicKey = extractBase64KeyFromPEM(pemPublicKey);
        final var publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
        final var keySpec = new X509EncodedKeySpec(publicKeyBytes);

        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    private String extractBase64KeyFromPEM(String pemPrivateKey) {
        return pemPrivateKey
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
    }
}