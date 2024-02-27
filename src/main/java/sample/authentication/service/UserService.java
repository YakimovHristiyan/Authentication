package sample.authentication.service;

import sample.authentication.model.payload.request.RegistrationRequest;
import sample.authentication.model.payload.response.AuthDetailsResponse;

public interface UserService {

    AuthDetailsResponse register(RegistrationRequest userRegistrationRequest);

    AuthDetailsResponse getById(Long id);
}