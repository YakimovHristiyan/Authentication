package sample.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import sample.authentication.model.payload.request.AuthenticationRequest;
import sample.authentication.model.payload.response.RefreshTokenResponse;
import sample.authentication.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<RefreshTokenResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        final var authenticationResponse = this.authService.login(authenticationRequest);
        final var headers = new HttpHeaders();
        headers.setBearerAuth(authenticationResponse.accessToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(RefreshTokenResponse.fromAuthenticationResponse(authenticationResponse));
    }

    @PutMapping("/access-token")
    public ResponseEntity<RefreshTokenResponse> renewToken(@RequestHeader("Authorization") String header) {
        final var authenticationResponse = this.authService.renewToken(header);
        final var headers = new HttpHeaders();
        headers.setBearerAuth(authenticationResponse.accessToken());

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(headers)
                .body(RefreshTokenResponse.fromAuthenticationResponse(authenticationResponse));
    }
}