package sample.authentication.model.payload.response;

public record RefreshTokenResponse(String refreshToken) {

    public static RefreshTokenResponse fromAuthenticationResponse(AuthenticationResponse res) {
        return new RefreshTokenResponse(res.refreshToken());
    }
}