package sample.authentication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sample.authentication.constants.HeaderConstants;
import sample.authentication.model.payload.request.RegistrationRequest;
import sample.authentication.model.payload.response.AuthDetailsResponse;
import sample.authentication.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthDetailsResponse register(@Valid @RequestBody RegistrationRequest userRegistrationRequest) {
        return this.userService.register(userRegistrationRequest);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AuthDetailsResponse getById(@RequestHeader(HeaderConstants.USER_ID) Long id) {
        return this.userService.getById(id);
    }
}