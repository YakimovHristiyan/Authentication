package sample.authentication.model.payload.response;

public record AuthenticationResponse(String accessToken, String refreshToken) {
}