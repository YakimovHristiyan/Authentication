package sample.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sample.authentication.config.AuthApiProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final static String ID_CLAIM = "Id";

    private final long jwtExpiration;
    private final long refreshExpiration;
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtServiceImpl(AuthApiProperties authApiProperties) {
        this.jwtExpiration = authApiProperties.getJwtExpirationInMilliSeconds();
        this.refreshExpiration = authApiProperties.getJwtRefreshExpirationInMilliSeconds();
        this.privateKey = authApiProperties.readPrivateKey(authApiProperties.getPrivateKeyPath());
        this.publicKey = authApiProperties.readPublicKey(authApiProperties.getPublicKeyPath());
    }

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public Long extractId(String token) {
        return Long.valueOf(extractClaim(token, Claims::getId));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = this.extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails, Long id) {
        final var extraClaims = new HashMap<String, Object>() {{
            put(ID_CLAIM, id);
        }};

        return this.generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return this.buildToken(extraClaims, userDetails, this.jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails, Long id) {
        final var extraClaims = new HashMap<String, Object>() {{
            put(ID_CLAIM, id);
        }};

        return this.buildToken(extraClaims, userDetails, this.refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setId(extraClaims.get(ID_CLAIM).toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        final var parsedJwt = Jwts.parserBuilder()
                .setSigningKey(this.publicKey)
                .build()
                .parseClaimsJws(token);

        return parsedJwt.getBody();
    }
}