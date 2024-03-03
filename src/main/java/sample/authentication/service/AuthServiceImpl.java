package sample.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sample.authentication.config.AuthApiProperties;
import sample.authentication.constant.MessageConstants;
import sample.authentication.exception.BadRequestException;
import sample.authentication.exception.UnauthorizedException;
import sample.authentication.model.entity.TokenType;
import sample.authentication.model.entity.User;
import sample.authentication.model.payload.request.AuthenticationRequest;
import sample.authentication.model.payload.response.AuthenticationResponse;
import sample.authentication.repository.UserRepository;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final static int indexToSubstringToken = TokenType.BEARER.getValue().length();

    private final RedisTemplate<String, String> redisTemplate;
    private final AuthApiProperties authApiProperties;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest request) {
        final var user = this.userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UnauthorizedException(MessageConstants.INVALID_USERNAME_PASSWORD));
        this.validateUserPassword(user, request);
        final var userDetails = this.userDetailsService.loadUserByUsername(request.username());
        final var accessToken = this.jwtService.generateToken(userDetails, user.getId());
        this.cacheToken(accessToken, user.getId());
        final var refreshToken = this.jwtService.generateRefreshToken(userDetails, user.getId());

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse renewToken(String authHeader) {
        final var refreshToken = authHeader.substring(indexToSubstringToken);
        final var username = this.jwtService.extractUsername(refreshToken);
        final var id = this.jwtService.extractId(refreshToken);
        final var userDetails = this.userDetailsService.loadUserByUsername(username);
        final var accessToken = this.jwtService.generateToken(userDetails, id);
        this.cacheToken(accessToken, id);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private void validateUserPassword(User user, AuthenticationRequest request) {
        final var encodedRealPassword = user.getPassword();
        if (!this.passwordEncoder.matches(request.password(), encodedRealPassword)) {
            throw new BadRequestException(MessageConstants.INVALID_USERNAME_PASSWORD);
        }
    }

    private void cacheToken(String token, Long userId) {
        this.redisTemplate.opsForValue().set(token, userId.toString());
        this.redisTemplate.expire(token, Duration.ofMillis(this.authApiProperties.getJwtExpirationInMilliSeconds()));
    }
}