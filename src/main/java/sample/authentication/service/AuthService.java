package sample.authentication.service;

import sample.authentication.model.payload.request.AuthenticationRequest;
import sample.authentication.model.payload.response.AuthenticationResponse;

public interface AuthService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse renewToken(String authHeader);
}