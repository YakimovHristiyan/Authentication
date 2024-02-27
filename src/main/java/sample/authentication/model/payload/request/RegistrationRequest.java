package sample.authentication.model.payload.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import sample.authentication.constants.MessageConstants;
import sample.authentication.constants.RegexConstants;

import java.time.LocalDate;

public record RegistrationRequest(

        @Pattern(regexp = RegexConstants.USER_NAME_REGEX, message = MessageConstants.INVALID_USERNAME)
        String username,

        @Pattern(regexp = RegexConstants.PASSWORD_REGEX, message = MessageConstants.INVALID_PASSWORD)
        String password,

        @Pattern(regexp = RegexConstants.EMAIL_REGEX, message = MessageConstants.INVALID_EMAIL)
        String email,

        @NotBlank(message = MessageConstants.FIELD_CANNOT_BE_NULL)
        String confirm,

        @Pattern(regexp = RegexConstants.NAME_REGEX, message = MessageConstants.INVALID_FORMAT_FOR_FIRST_NAME)
        String firstName,

        @Pattern(regexp = RegexConstants.USER_NAME_REGEX, message = MessageConstants.INVALID_FORMAT_FOR_LAST_NAME)
        String lastName,

        @Past(message = MessageConstants.INVALID_DATE)
        LocalDate bornOn) {

    @AssertTrue(message = MessageConstants.PASSWORD_MISMATCH)
    public boolean isPasswordMatch() {
        return password().equals(confirm());
    }
}