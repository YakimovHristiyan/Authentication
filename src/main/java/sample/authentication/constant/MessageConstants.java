package sample.authentication.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageConstants {

    public static final String INVALID_USERNAME_PASSWORD = "Invalid username or password!";
    public static final String USER_NOT_FOUND = "User with id: %d not found!";
    public static final String USER_EXISTS = "A user with the given username already exists.";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error!";
    public static final String INVALID_FORMAT_FOR_FIRST_NAME = "Invalid format for first name. Name must start with " +
            "capital letter and contain only characters.";
    public static final String INVALID_FORMAT_FOR_LAST_NAME = "Invalid format for last name. Name must start with " +
            "capital letter and contain only characters.";
    public static final String INVALID_DATE = "Invalid date.";
    public static final String PASSWORD_MISMATCH = "Password and confirm password do not match.";
    public static final String FIELD_CANNOT_BE_NULL = "Field can not be null.";
    public static final String INVALID_USERNAME = "Invalid format for username. Must be at least 3 symbols long.";
    public static final String INVALID_PASSWORD = "Invalid password. Password must contain at least " +
            "one uppercase letter, number or symbol and be of minimum length 8";
    public static final String INVALID_EMAIL = "Invalid email!";
}